package africa.semicolon.ppay.application.ports.input.transactionUseCase;

import africa.semicolon.ppay.domain.model.Transaction;

import java.util.List;

public interface ViewAllTransactionUseCase {
    List<Transaction> viewAllTransactions(Long userId);
}
