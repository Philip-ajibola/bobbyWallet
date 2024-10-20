package africa.semicolon.bobbywallet.adapter.output.repository;

import africa.semicolon.bobbywallet.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
