package africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepo extends JpaRepository<UserEntity,Long> {
}
