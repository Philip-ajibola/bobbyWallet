package africa.semicolon.ppay.domain.exception;

public class InvalidUserCredentials extends PPayWalletException{
    public InvalidUserCredentials(String invalidUserCredentials) {
        super(invalidUserCredentials);

    }
}
