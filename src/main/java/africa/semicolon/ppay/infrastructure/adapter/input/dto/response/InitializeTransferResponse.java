package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeTransferResponse {
    @JsonProperty("status")
    private boolean status;
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
        @JsonProperty("reference")
        private String reference;
        @JsonProperty("integration")
        private Long integration;
        @JsonProperty("domain")
        private String domain;
        @JsonProperty("amount")
        private Long amount;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("source")
        private String source;
        @JsonProperty("reason")
        private String reason;
        @JsonProperty("recipient")
        private Long recipient;
        @JsonProperty("status")
        private String status;
        @JsonProperty("transfer_code")
        private String transferCode;
        @JsonProperty("id")
        private Long id;
        @JsonProperty("createdAt")
        private Date createdAt;
        @JsonProperty("updatedAt")
        private Date updatedAt;
    }
}
