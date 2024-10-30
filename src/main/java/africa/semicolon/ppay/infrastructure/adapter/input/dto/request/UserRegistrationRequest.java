package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationRequest {
    @NotBlank(message = "First name must not be empty or null")
    private String firstname;
    @NotBlank(message = "Last name must not be empty or null")
    private String lastname;
    @NotBlank(message = "Password must not be empty or null")
    private String password;
    @NotBlank(message = "Email must not be empty or null")
    @Email(message = "Provide a valid email")
    private String email;

}
