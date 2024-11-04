package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.service.TransactionService;
import africa.semicolon.ppay.domain.model.Transaction;

import java.util.List;

public interface GetAllTransactionUseCase {
    List<Transaction> getAllTransactions(Long userId, TransactionService service);
}
