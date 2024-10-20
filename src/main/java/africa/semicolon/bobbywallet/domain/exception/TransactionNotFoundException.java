package africa.semicolon.bobbywallet.domain.exception;

public class TransactionNotFoundException extends BobbyWalletException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
