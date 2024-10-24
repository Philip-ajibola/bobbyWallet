package africa.semicolon.ppay.domain.service;

import africa.semicolon.ppay.application.ports.input.transactionUseCase.SaveTransactionUseCase;
import africa.semicolon.ppay.application.ports.input.transactionUseCase.ViewASingleTransactionUseCase;
import africa.semicolon.ppay.application.ports.input.transactionUseCase.ViewAllTransactionUseCase;
import africa.semicolon.ppay.application.ports.output.TransactionOutputPort;
import africa.semicolon.ppay.domain.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TransactionService implements SaveTransactionUseCase, ViewAllTransactionUseCase,ViewASingleTransactionUseCase {
    private final TransactionOutputPort transactionOutputPort;

    public TransactionService(TransactionOutputPort transactionOutputPort) {
        this.transactionOutputPort = transactionOutputPort;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionOutputPort.saveTransaction(transaction);
    }

    @Override
    public Transaction viewTransaction(Long id) {
        return transactionOutputPort.getTransactionById(id);
    }

    @Override
    public List<Transaction> viewAllTransactions(Long userId, @Autowired WalletService walletService) {
        return transactionOutputPort.getAllTransactions(userId,walletService);
    }
}
