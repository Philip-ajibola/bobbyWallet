package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.model.User;

public interface FindByIdUseCase {
    User findById(Long id);
}
