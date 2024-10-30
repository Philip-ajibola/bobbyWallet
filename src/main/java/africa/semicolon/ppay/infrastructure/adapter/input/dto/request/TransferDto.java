package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferDto {
    @JsonProperty("account_number")
    @NotBlank(message = "Account number must not be null or empty")
    private String accountNumber;
    @JsonProperty("name")
    @NotBlank(message = "name must not be null or empty")
    private String name;
    @NotBlank(message = "Bank code must not be null or empty")
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("user_id")
    @NotBlank(message = "id must not be null or empty")
    private Long id;
    @JsonProperty("amount")
    @NotBlank(message = "Amount must not be null or empty")
    private BigDecimal amount;
    @JsonProperty("pin")
    @NotBlank(message = "pin must not be null or empty")
    private String pin;

}
