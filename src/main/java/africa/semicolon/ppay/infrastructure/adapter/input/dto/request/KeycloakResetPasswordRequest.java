package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class KeycloakResetPasswordRequest {
    @NotBlank(message = "Password must not be null")
    private  final String password;
    @NotBlank(message = "Username must not be null")
    private final String username;
    private boolean temporary=false;
}
