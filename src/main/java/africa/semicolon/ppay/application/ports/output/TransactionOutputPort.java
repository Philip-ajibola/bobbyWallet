package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.service.WalletService;

import java.util.List;

public interface TransactionOutputPort {
    Transaction saveTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions(Long userId, WalletService walletService);
}
