package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationRequest {
    private String firstname;
    private String lastname;
    private String password;
    private String email;

}
