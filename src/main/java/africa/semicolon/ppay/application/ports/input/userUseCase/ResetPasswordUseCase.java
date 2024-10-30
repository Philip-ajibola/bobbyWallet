package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.UserResponse;

public interface ResetPasswordUseCase {
    UserResponse resetPassword(ResetPasswordRequest request);
}
