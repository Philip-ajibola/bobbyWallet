package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
    //@JsonIgnoreProperties(ignoreUnknown = true)
public class MonifyVerifyTransactionResponse {
        private boolean requestSuccessful;
        private String responseMessage;
        private String responseCode;
        private ResponseBody responseBody;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ResponseBody {
            private String transactionReference;
            private String paymentReference;
            private String amountPaid;
            private String totalPayable;
            private String settlementAmount;
            private String paidOn;
            private String paymentStatus;
            private String paymentDescription;
            private String currency;
            private String paymentMethod;
            private Product product;
            private CardDetails cardDetails;
            private AccountDetails accountDetails;
            private List<AccountPayments> accountPayments;
            private Customer customer;
            private MetaData metaData;

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class Product {
                private String type;
                private String reference;
            }
        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class CardDetails {
                private String cardType;
                private String last4;
                private String expMonth;
                private String expYear;
                private String bin;
                private String bankCode;
                private String bankName;
                private boolean reusable;
                private String countryCode;
                private String cardToken;
                private boolean supportsTokenization;
                private String maskedPan;
            }

            public static class AccountDetails {
                // Define fields here if needed, assuming it's non-null in some responses.
            }

            public static class AccountPayments {
                // Define fields here if needed, assuming it's non-null in some responses.
            }

            public static class Customer {
                private String email;
                private String name;
            }

            public static class MetaData {
                // Define fields here if needed.
            }
        }


}
