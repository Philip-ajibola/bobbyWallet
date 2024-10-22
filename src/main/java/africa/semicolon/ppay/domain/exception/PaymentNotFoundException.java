package africa.semicolon.ppay.domain.exception;

public class PaymentNotFoundException extends PPayWalletException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
