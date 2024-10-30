package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DepositDto {
    @NotBlank(message = "Id must not be null")
    private Long id;
    @NotBlank(message = "amount must not be null")
    private BigDecimal amount;
    @NotBlank(message = "amount must not be null")
    @Email(message = "provide valid email")
    private String email;
}
