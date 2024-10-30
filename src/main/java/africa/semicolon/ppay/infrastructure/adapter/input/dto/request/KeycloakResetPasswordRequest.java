package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class KeycloakResetPasswordRequest {
    private  final String password;
    private final String username;
    private boolean temporary=false;
}
