package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferDto {
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("user_id")
    private Long id;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("pin")
    private String pin;

}
