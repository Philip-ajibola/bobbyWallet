package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawDto {
    @NotBlank(message = "Id must not be null or empty")
    private Long id;
    @NotBlank(message = "amount must not be null or empty")
    private BigDecimal amount;
}
