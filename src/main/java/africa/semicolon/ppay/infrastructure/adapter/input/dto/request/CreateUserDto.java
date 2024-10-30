package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "PhoneNumber is mandatory")
    private String phoneNumber;
    @NotBlank(message = "User role is mandatory")
    private String role;
}
