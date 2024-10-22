package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentVerificationResponse {

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Data data;

    @Override
    public String toString() {
        return "PaymentVerificationResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        @JsonProperty("status")
        private String status;

        @JsonProperty("reference")
        private String reference;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("gateway_response")
        private String gatewayResponse;

        @JsonProperty("paid_at")
        private String paidAt;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("channel")
        private String channel;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("ip_address")
        private String ipAddress;

        @JsonProperty("pricing_plan_type")
        private String pricingPlanType;

        @JsonProperty("created_on")
        private Date createdOn = new Date();

        @JsonProperty("updated_on")
        private Date updatedOn = new Date();

        @Override
        public String toString() {
            return "Data{" +
                    "status='" + status + '\'' +
                    ", reference='" + reference + '\'' +
                    ", amount=" + amount +
                    ", gatewayResponse='" + gatewayResponse + '\'' +
                    ", paidAt='" + paidAt + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", channel='" + channel + '\'' +
                    ", currency='" + currency + '\'' +
                    ", ipAddress='" + ipAddress + '\'' +
                    ", pricingPlanType='" + pricingPlanType + '\'' +
                    ", createdOn=" + createdOn +
                    ", updatedOn=" + updatedOn +
                    '}';
        }
    }
}
