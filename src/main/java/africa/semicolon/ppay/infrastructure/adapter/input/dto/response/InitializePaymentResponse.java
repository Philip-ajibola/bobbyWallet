package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentResponse {

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Data{

        @JsonProperty("authorization_url")
        private String authorizationUrl;

        @JsonProperty("access_code")
        private String accessCode;

        @JsonProperty("reference")
        private String reference;

    }
    @Override
    public String toString() {
        return "InitializePaymentResponse{" +
                    "status: " + status +
                    ", message: '" + message + '\'' +
                    ", data:{" +
                    "authorizationUrl: " + data.authorizationUrl +" " +
                    "accessCode: " + data.accessCode +" " +
                    "reference: " + data.reference +" " +
                    "}" +
                '}';
    }
}
