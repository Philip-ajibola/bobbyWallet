package africa.semicolon.bobbywallet.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String firstname;
    private String lastname;
    private String password;
    private String email;
}
