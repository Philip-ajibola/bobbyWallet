package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizeTransactionResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;
    private ResponseBody responseBody;
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseBody {
        private int amount;
        private String reference;
        private String status;
        private String dateCreated;
        private int totalFee;
        private String destinationAccountName;
        private String destinationBankName;
        private String destinationAccountNumber;
        private String destinationBankCode;
    }

}
