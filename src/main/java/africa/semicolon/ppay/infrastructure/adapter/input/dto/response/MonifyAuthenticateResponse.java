package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class MonifyAuthenticateResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private ResponseBody responseBody;
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseBody {
        private String accessToken;
        private int expiresIn;
    }
}
