package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DepositDto {
    private Long id;
    private BigDecimal amount;
    private String email;
}
