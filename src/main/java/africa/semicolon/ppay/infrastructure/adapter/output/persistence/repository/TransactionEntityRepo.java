package africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionEntityRepo extends JpaRepository<TransactionEntity,Long> {
    @Query("SELECT T FROM TransactionEntity T WHERE T.wallet.id =:walletId")
    List<TransactionEntity> findAllTransactionsBy(Long walletId);
}
