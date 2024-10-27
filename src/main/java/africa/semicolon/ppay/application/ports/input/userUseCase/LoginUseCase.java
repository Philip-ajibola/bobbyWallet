package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.LoginRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
