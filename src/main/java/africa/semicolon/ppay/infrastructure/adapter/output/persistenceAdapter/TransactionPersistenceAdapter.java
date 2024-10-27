package africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter;

import africa.semicolon.ppay.application.ports.output.TransactionOutputPort;
import africa.semicolon.ppay.domain.exception.TransactionNotFoundException;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.infrastructure.adapter.output.mappers.EntityMappers;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.TransactionEntityRepo;

import java.util.List;


public class TransactionPersistenceAdapter implements TransactionOutputPort {
    private final TransactionEntityRepo transactionEntityRepo;

    public TransactionPersistenceAdapter(TransactionEntityRepo transactionEntityRepo) {
        this.transactionEntityRepo = transactionEntityRepo;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = EntityMappers.INSTANCE.toEntity(transaction);
        transactionEntity = transactionEntityRepo.save(transactionEntity);
        return EntityMappers.INSTANCE.toModel(transactionEntity);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        TransactionEntity transactionEntity = transactionEntityRepo.findById(id).orElseThrow(()->new TransactionNotFoundException(String.format("Transaction with %s not found",id)));
        return EntityMappers.INSTANCE.toModel(transactionEntity);
    }

    @Override
    public List<Transaction> getAllTransactions(Long walletId) {
        List<TransactionEntity> transactionEntities = transactionEntityRepo.findAllTransactionsBy(walletId);
        return transactionEntities.stream().map(TransactionEntity->EntityMappers.INSTANCE.toModel(TransactionEntity)).toList();
    }
}
