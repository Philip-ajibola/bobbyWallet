package africa.semicolon.ppay.application.ports.input.transactionUseCase;

import africa.semicolon.ppay.domain.model.Transaction;

public interface ViewASingleTransactionUseCase {
    Transaction viewTransaction(Long id);
}
