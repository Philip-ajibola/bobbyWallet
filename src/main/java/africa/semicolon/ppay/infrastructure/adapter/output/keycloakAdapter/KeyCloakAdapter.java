package africa.semicolon.ppay.infrastructure.adapter.output.keycloakAdapter;

import africa.semicolon.ppay.application.ports.output.IdentityManagerOutputPort;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.KeycloakResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.LoginResponse;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
public class KeyCloakAdapter  implements IdentityManagerOutputPort {
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${token-server-url}")
    private String tokenServerUrl;
    private RestTemplate restTemplate;
    private Keycloak keycloak;

    public KeyCloakAdapter(RestTemplate restTemplate, Keycloak keycloak) {
        this.restTemplate = restTemplate;
        this.keycloak = keycloak;
    }

    @Override
    public User registerUser(User request) {
        CredentialRepresentation credentials = getCredentialRepresentation(request.getPassword());
        UserRepresentation user = getUserRepresentation(request, credentials);
        Response response =  getUserResource().create(user);
        System.out.println(response.getStatus());
        if(response.getStatus() !=201){
            throw new PPayWalletException("KeyCloak: Failed to register user reason: invalid detail or email already exist");
        }
        String userId = getUserByUsername(request.getEmail()).getId();
        assignRoleToUser(userId, request.getRole());
        request.setKeyCloakId(userId);
        sendVerificationEmail(userId);
        return request;
    }
    private void sendVerificationEmail(String userId){
        UsersResource usersResource = getUserResource();
        usersResource.get(userId).sendVerifyEmail();

    }

    private static UserRepresentation getUserRepresentation(User request, CredentialRepresentation credentials) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getEmail());
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setCredentials(Collections.singletonList(credentials));
        user.setEmailVerified(false);
        user.setEnabled(true);
        return user;
    }

    private CredentialRepresentation getCredentialRepresentation(String password) {
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setTemporary(false);
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(password);
        return credentials;
    }

    public UsersResource getUserResource(){
        return keycloak.realm(realm).users();
    }


    @Override
    public LoginResponse login(String username, String password) {
        MultiValueMap<String, String> body = createClientRequest(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<LoginResponse> response = restTemplate.postForEntity(tokenServerUrl, request, LoginResponse.class);
            System.out.println("It happen here");
            log.info("response body " + response.getBody());
            return response.getBody();
        }catch (HttpClientErrorException e){
            log.info("Exception Message {}" + e);
            throw new AuthenticationServiceException("Authentication failed: ",e);
        }

    }

    private MultiValueMap<String, String> createClientRequest(String username, String password) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type","password");
        body.add("username", username);
        body.add("password", password);
        return body;
    }
    private void assignRoleToUser(String userId, String roleName) {
        UserResource userResource = getUserResource().get(userId);
        RolesResource rolesResource = keycloak.realm(realm).roles();

        RoleRepresentation role = rolesResource.get(roleName).toRepresentation();


        userResource.roles().realmLevel().add(Collections.singletonList(role));
        log.info("Assigned role '{}' to user with ID: {}", roleName, userId);
    }
    private UserRepresentation getUserByUsername(String username) {
        List<UserRepresentation> users = getUserResource().search(username);
        if (users.isEmpty()) {
            throw new PPayWalletException("User not found");
        }
        return users.get(0);
    }

    @Override
    public void deleteUser(String userId) {
        UsersResource userResource = getUserResource();
        userResource.delete(userId);
    }

    @Override
    public void forgetPassword(KeycloakResetPasswordRequest request) {

        UserRepresentation user = getUserByUsername(request.getUsername());
        UsersResource usersResource = getUserResource();

        UserResource userResource = usersResource.get(user.getId());

        userResource.resetPassword(getCredentialRepresentation(request.getPassword()));
    }

    public void updateUser( User updatedUser) {
        System.out.println(updatedUser.getKeyCloakId());
        UserResource userResource = getUserResource().get(updatedUser.getKeyCloakId());
        UserRepresentation userRepresentation = userResource.toRepresentation();

        userRepresentation.setUsername(updatedUser.getEmail());
        userRepresentation.setFirstName(updatedUser.getFirstname());
        userRepresentation.setLastName(updatedUser.getLastname());
        userRepresentation.setEmail(updatedUser.getEmail());

        System.out.println("Success here");
        userResource.update(userRepresentation);
        log.info("User updated successfully.");
    }
}
