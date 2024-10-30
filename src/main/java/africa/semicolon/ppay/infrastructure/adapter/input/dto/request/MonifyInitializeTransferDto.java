package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonifyInitializeTransferDto {
    @NotBlank(message = "Amount must not be null or empty")
    private BigDecimal amount;
    @NotBlank(message = "Reference must not be null or empty")
    private String reference;
    private String narration;
    @NotBlank(message = "Destination bank code must not be null or empty")
    private String destinationBankCode;
    @NotBlank(message = "Destination account number must not be null or empty")
    private String destinationAccountNumber;
    private String currency="NGN";
    private String sourceAccountNumber;
}
