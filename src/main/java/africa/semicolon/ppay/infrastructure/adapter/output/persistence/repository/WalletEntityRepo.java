package africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletEntityRepo extends JpaRepository<WalletEntity,Long> {

}
