package africa.semicolon.ppay.application.ports.input.KeycloakUseCase;

import africa.semicolon.ppay.domain.model.User;

public interface RegisterUseCase {
    User registerUser(User user);
}
