package africa.semicolon.ppay.domain.exception;

public class WalletNotFoundException extends PPayWalletException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
