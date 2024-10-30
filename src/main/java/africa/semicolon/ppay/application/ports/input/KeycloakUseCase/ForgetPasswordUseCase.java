package africa.semicolon.ppay.application.ports.input.KeycloakUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.KeycloakResetPasswordRequest;

public interface ForgetPasswordUseCase {
    void forgetPassword(KeycloakResetPasswordRequest request);
}
