package africa.semicolon.ppay.application.ports.input.transactionUseCase;

import africa.semicolon.ppay.domain.model.Transaction;

import java.util.List;

public interface GetAllTransactionUseCase {
    List<Transaction> getAllTransaction();
}
