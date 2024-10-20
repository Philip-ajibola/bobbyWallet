package africa.semicolon.bobbywallet.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateTransferRecipientDto {
    @JsonProperty("type")
    @Setter(AccessLevel.NONE)
    private String type = "nuban";
    @NonNull
    @JsonProperty("account_number")
    private String account_number;
    @NonNull
    @JsonProperty("name")
    private String name;
    @NonNull
    @JsonProperty("bank_code")
    private String bank_code;
    @JsonProperty("currency")
    @Setter(AccessLevel.NONE)
    private String currency ="NGN";


}
