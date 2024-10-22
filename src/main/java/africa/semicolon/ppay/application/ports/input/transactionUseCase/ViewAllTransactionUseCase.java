package africa.semicolon.ppay.application.ports.input.transactionUseCase;

import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.service.WalletService;

import java.util.List;

public interface ViewAllTransactionUseCase {
    List<Transaction> viewAllTransactions(Long userId, WalletService walletService);
}
