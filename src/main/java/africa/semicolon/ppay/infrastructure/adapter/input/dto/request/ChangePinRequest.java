package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangePinRequest {
    @NotNull(message = "id must not be null")
    private Long id;
    @NotNull(message = "field must not be null")
    private String newPin;
    @NotNull(message = "field must not be null")
    private String password;

}
