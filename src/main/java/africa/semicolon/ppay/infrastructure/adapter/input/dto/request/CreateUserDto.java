package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @Size(min = 3, message = "Firstname must not be less than 3 words")
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @NotBlank(message = "Lastname is mandatory")
    @Size(min = 3, message = "Lastname must not be less than 3 words")
    private String lastname;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "PhoneNumber is mandatory")
    @Size(min = 11, max = 11, message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "User role is mandatory")
    private String role;
}
