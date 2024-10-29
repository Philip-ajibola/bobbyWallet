package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyTransferResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private ResponseBody responseBody;
    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseBody {
        private int amount;
        private String reference;
        private String narration;
        private String currency;
        private int fee;
        private boolean twoFaEnabled;
        private String status;
        private String transactionDescription;
        private String transactionReference;
        private String createdOn;
        private String sourceAccountNumber;
        private String destinationAccountNumber;
        private String destinationAccountName;
        private String destinationBankCode;
        private String destinationBankName;
    }
}
