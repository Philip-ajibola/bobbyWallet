package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransferRecipientResponse {
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
    public static class Data{
        @JsonProperty("active")
        private boolean active;
        @JsonProperty("createdAt")
        private Date createdAt;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("domain")
        private String domain;
        @JsonProperty("id")
        private Long id;
        @JsonProperty("integration")
        private Long integration;
        @JsonProperty("name")
        private String name;
        @JsonProperty("recipient_code")
        private String recipientCode;
        @JsonProperty("type")
        private String type;
        @JsonProperty("updatedAt")
        private Date updatedAt;
        @JsonProperty("is_deleted")
        private boolean isDeleted;
        private Details details;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Details {
            @JsonProperty("authorization_code")
            private String authorization_code;
            @JsonProperty("account_number")
            private String account_number;
            @JsonProperty("account_name")
            private String account_name;
            @JsonProperty("bank_code")
            private String bank_code;
            @JsonProperty("bank_name")
            private String bank_name;
        }


    }
}
