package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class VerifyPaymentDto {
    private String reference;
    private BigDecimal amount;
    private Long id;
}
