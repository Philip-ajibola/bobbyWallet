package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferVerificationResponse {

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Data data;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("domain")
        private String domain;

        @JsonProperty("failures")
        private String failures;

        @JsonProperty("id")
        private long id;

        @JsonProperty("integration")
        private long integration;

        @JsonProperty("reason")
        private String reason;

        @JsonProperty("reference")
        private String reference;

        @JsonProperty("source")
        private String source;

        @JsonProperty("source_details")
        private String sourceDetails;

        @JsonProperty("status")
        private String status;

        @JsonProperty("titan_code")
        private String titanCode;

        @JsonProperty("transfer_code")
        private String transferCode;

        @JsonProperty("request")
        private long request;

        @JsonProperty("transferred_at")
        private String transferredAt;

        @JsonProperty("updatedAt")
        private String updatedAt;

        @JsonProperty("recipient")
        private String recipient;

        @JsonProperty("session")
        private Session session;

        @JsonProperty("fee_charged")
        private int feeCharged;

        @JsonProperty("fees_breakdown")
        private String feesBreakdown;

        @JsonProperty("gateway_response")
        private String gatewayResponse;

        @Setter
        @Getter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Recipient {

            @JsonProperty("active")
            private boolean active;

            @JsonProperty("createdAt")
            private String createdAt;

            @JsonProperty("currency")
            private String currency;

            @JsonProperty("description")
            private String description;

            @JsonProperty("domain")
            private String domain;

            @JsonProperty("email")
            private String email;

            @JsonProperty("id")
            private long id;

            @JsonProperty("integration")
            private long integration;

            @JsonProperty("metadata")
            private Metadata metadata;

            @JsonProperty("name")
            private String name;

            @JsonProperty("recipient_code")
            private String recipientCode;

            @JsonProperty("type")
            private String type;

            @JsonProperty("updatedAt")
            private String updatedAt;

            @JsonProperty("is_deleted")
            private boolean isDeleted;

            @JsonProperty("details")
            private Details details;

            @Setter
            @Getter
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Metadata {

                @JsonProperty("custom_fields")
                private CustomField[] customFields;
                @Setter
                @Getter
                @JsonInclude(JsonInclude.Include.NON_NULL)
                @AllArgsConstructor
                @NoArgsConstructor
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class CustomField {

                    @JsonProperty("display_name")
                    private String displayName;

                    @JsonProperty("variable_name")
                    private String variableName;

                    @JsonProperty("value")
                    private String value;


                }
            }
            @Setter
            @Getter
            @JsonInclude(JsonInclude.Include.NON_NULL)
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Details {

                @JsonProperty("authorization_code")
                private String authorizationCode;

                @JsonProperty("account_number")
                private String accountNumber;

                @JsonProperty("account_name")
                private String accountName;

                @JsonProperty("bank_code")
                private String bankCode;

                @JsonProperty("bank_name")
                private String bankName;

            }
        }
        @Setter
        @Getter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Session {

            @JsonProperty("provider")
            private String provider;

            @JsonProperty("id")
            private String id;

            // Getters and Setters
        }
    }
}
