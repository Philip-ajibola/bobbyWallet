package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.model.User;

public interface DeleteUserUseCase {
    void delete(Long userId);
    void delete(User user);
}
