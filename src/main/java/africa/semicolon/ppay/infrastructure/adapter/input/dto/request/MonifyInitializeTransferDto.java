package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonifyInitializeTransferDto {
    private BigDecimal amount;
    private String reference;
    private String narration;
    private String destinationBankCode;
    private String destinationAccountNumber;
    private String currency="NGN";
    private String sourceAccountNumber;
}
