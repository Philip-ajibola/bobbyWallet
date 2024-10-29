package africa.semicolon.ppay.infrastructure.adapter.input.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class MonifyInitializePaymentRequest {
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
    @Setter(AccessLevel.NONE)
    private String paymentReference = "BbW-" + System.currentTimeMillis();
    private String paymentDescription;
    @Setter(AccessLevel.NONE)
    private String currencyCode = "NGN";
    private String contractCode;
    @Setter(AccessLevel.NONE)
    private String redirectUrl = "http://localhost:8085/api/v1/wallet/pay_completed";
    @Setter(AccessLevel.NONE)
    private List<String> paymentMethods = List.of("CARD","ACCOUNT_TRANSFER");

}
