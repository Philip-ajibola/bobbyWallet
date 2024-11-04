package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.User;

import java.util.List;

public interface UserOutputPort {
    User saveUser(User user);
    User getUserById(Long id);
    void delete(Long id);
    void delete(User user);
    boolean existsById(Long id);

    User findUserByEmail(String email);

    List<User> getAllUsers();


}
