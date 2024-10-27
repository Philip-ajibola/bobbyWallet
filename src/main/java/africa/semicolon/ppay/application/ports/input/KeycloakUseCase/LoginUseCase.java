package africa.semicolon.ppay.application.ports.input.KeycloakUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(String username,String password);
}
