package africa.semicolon.bobbywallet.adapter.output.repository;

import africa.semicolon.bobbywallet.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
