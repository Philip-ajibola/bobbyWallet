package africa.semicolon.bobbywallet.domain.exception;

public class UserNotFoundException extends BobbyWalletException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
