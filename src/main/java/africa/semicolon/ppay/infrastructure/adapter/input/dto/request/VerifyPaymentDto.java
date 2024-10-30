package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class VerifyPaymentDto {
    @NotBlank(message = "reference must not be null or empty")
    private String reference;
    @NotBlank(message = "amount must not be null or empty")
    private BigDecimal amount;
    @NotBlank(message = "id must not be null or empty")
    private Long id;
}
