package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePinRequest {
    private Long id;
    private String newPin;
    private String password;

}
