package africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayStackPaymentEntityRepo extends JpaRepository<PaymentEntity,Long> {
}
