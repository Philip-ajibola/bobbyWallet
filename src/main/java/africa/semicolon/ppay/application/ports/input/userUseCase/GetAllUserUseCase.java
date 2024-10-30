package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.model.User;

import java.util.List;

public interface GetAllUserUseCase {
    List<User>  getAllUsers();
}
