package africa.semicolon.bobbywallet.adapter.output.repository;


import africa.semicolon.bobbywallet.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    @Query("SELECT T FROM Transaction  T WHERE T.wallet.id=:userId")
    List<Transaction> findAllTransactionsBy(Long userId);
}
