package africa.semicolon.ppay.domain.exception;

public class TransactionNotFoundException extends PPayWalletException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
