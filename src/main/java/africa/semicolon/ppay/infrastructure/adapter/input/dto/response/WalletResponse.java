package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse<T> {
    private Long id;
    private BigDecimal balance;
    private T data;
}
