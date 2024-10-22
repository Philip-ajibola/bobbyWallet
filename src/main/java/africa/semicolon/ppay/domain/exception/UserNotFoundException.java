package africa.semicolon.ppay.domain.exception;

public class UserNotFoundException extends PPayWalletException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
