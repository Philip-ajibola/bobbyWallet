package africa.semicolon.ppay.domain.exception;

public class InvalidAmountException extends PPayWalletException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
