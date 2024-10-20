package africa.semicolon.bobbywallet.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositDto {
    private Long id;
    private BigDecimal amount;
}
