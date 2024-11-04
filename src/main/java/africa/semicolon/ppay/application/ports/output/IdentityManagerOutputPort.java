package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.KeycloakResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.LoginResponse;

public interface IdentityManagerOutputPort {
    User registerUser(User request);
    LoginResponse login(String username, String password);
    void deleteUser(String userId);
    void forgetPassword(KeycloakResetPasswordRequest request);
    void updateUser( User updatedUser);
}
