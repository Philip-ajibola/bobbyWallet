package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawDto {
    private Long id;
    private BigDecimal amount;
}
