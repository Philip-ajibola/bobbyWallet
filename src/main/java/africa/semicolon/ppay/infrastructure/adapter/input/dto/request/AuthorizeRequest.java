package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizeRequest {
    private String reference;
    private String authorizationCode;
}
