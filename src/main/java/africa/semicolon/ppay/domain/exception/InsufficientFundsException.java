package africa.semicolon.ppay.domain.exception;

public class InsufficientFundsException extends PPayWalletException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
