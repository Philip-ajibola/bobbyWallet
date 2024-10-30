package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthorizeRequest {
    @NotBlank(message = "Reference must not be null")
    private String reference;
    @NotBlank(message = "Reference must not be null")
    private String authorizationCode;
}
