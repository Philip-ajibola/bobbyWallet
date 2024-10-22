package africa.semicolon.ppay.application.ports.input.transactionUseCase;

import africa.semicolon.ppay.domain.model.Transaction;

public interface SaveTransactionUseCase {
    Transaction save(Transaction transaction);
}
