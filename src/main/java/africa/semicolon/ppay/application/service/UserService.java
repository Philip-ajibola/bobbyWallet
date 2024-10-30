package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.application.ports.input.userUseCase.*;
import africa.semicolon.ppay.application.ports.output.UserOutputPort;
import africa.semicolon.ppay.domain.exception.InvalidUserCredentials;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.KeycloakResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.LoginRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.LoginResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.UserResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.mappers.DtoMappers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService implements CreateUserUseCase,FindByEmailUseCase, FindByIdUseCase,LoginUseCase, UpdateUserUseCase, DeleteUserUseCase, ExistByIdUseCase,ChangePinUseCase,ResetPasswordUseCase {
    private final UserOutputPort userOutputPort;
    private final KeycloakUserService keycloakUserService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder;

    public UserService(UserOutputPort userOutputPort, KeycloakUserService keycloakUserService, PasswordEncoder passwordEncoder) {
        this.userOutputPort = userOutputPort;
        this.keycloakUserService = keycloakUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void delete(Long userId) {
        User user = findById(userId);
        String id = user.getKeyCloakId();
        userOutputPort.delete(userId);
        keycloakUserService.deleteUser(id);
    }

    @Override
    public void delete(User user) {
        userOutputPort.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userOutputPort.getUserById(id);
    }

    @Override
    public User createUser(User user,WalletService walletService) {
        user = keycloakUserService.registerUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         user = userOutputPort.saveUser(user);
        Wallet wallet = walletService.createWallet(user.getId());
        System.out.println(user.getId());
        user.setWallet(wallet);
        return userOutputPort.saveUser(user);
    }

    @Override
    public User updateUser(Long id, JsonPatch request)  {

        User user = findById(id);
        try{
            JsonNode jsonNode = objectMapper.convertValue(user, JsonNode.class);
            jsonNode = request.apply(jsonNode);
            user = objectMapper.convertValue(jsonNode, User.class);
            user = userOutputPort.saveUser(user);
            keycloakUserService.updateUser(user);
            return user;

        }catch (Exception e){
            throw new PPayWalletException(e.getMessage());
        }

    }

    @Override
    public boolean existById(Long id) {
        return userOutputPort.existsById(id);
    }

    @Override
    public User changePin(Long userId, String password, String newPin,WalletService walletService) {
        User user = userOutputPort.getUserById(userId);
        if(!passwordEncoder.matches(password,user.getPassword()))throw new PPayWalletException("Invalid Credential For THis User");

        Wallet wallet =  walletService.updatePin(userId,newPin);
       user.setWallet(wallet);
       return userOutputPort.saveUser(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
       return keycloakUserService.login(request.getEmail(),request.getPassword());
    }

    @Override
    public User findByEmail(String email) {
        User user = userOutputPort.findUserByEmail(email);
        return user;
    }

    @Override
    public UserResponse resetPassword(ResetPasswordRequest  request) {
        User user = findById(request.getId());
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))throw new InvalidUserCredentials("Invalid User Credentials Provided");
        KeycloakResetPasswordRequest resetPasswordRequest = new KeycloakResetPasswordRequest(request.getNewPassword(),user.getEmail());
        keycloakUserService.forgetPassword(resetPasswordRequest);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user = userOutputPort.saveUser(user);
        return DtoMappers.INSTANCE.toUserResponse(user);
    }
}
