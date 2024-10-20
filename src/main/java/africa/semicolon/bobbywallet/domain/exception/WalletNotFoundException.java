package africa.semicolon.bobbywallet.domain.exception;

public class WalletNotFoundException extends BobbyWalletException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
