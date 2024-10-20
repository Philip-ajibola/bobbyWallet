package africa.semicolon.bobbywallet.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class InitializeTransferDto {
    @JsonProperty("source")
    @Setter(AccessLevel.NONE)
    private final String source = "balance" ;
    @JsonProperty("user_id")
    @NonNull
    private Long id;
    @NonNull
    @JsonProperty("amount")
    private BigDecimal amount;
    @NonNull
    @JsonProperty("recipient")
    private String recipient; // recipient's unique recipient code. This is the recipient's account number or mobile number. For bank transfers, this is the bank account number.

}
