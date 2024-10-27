package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.Transaction;

import java.util.List;

public interface TransactionOutputPort {
    Transaction saveTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions(Long userId);
}
