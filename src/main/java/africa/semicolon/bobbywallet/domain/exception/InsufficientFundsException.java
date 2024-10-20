package africa.semicolon.bobbywallet.domain.exception;

public class InsufficientFundsException extends BobbyWalletException{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
