package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentDto {

        @JsonProperty("amount")
        private BigDecimal amount;
        @JsonProperty("user_id")
        private Long id;
        @JsonProperty("email")
        @NotNull(message = "Email Must Not be null")
        private String email;

        @JsonProperty("currency")
        @Setter(AccessLevel.NONE)
        private String currency = "NGN";

}
