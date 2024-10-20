package africa.semicolon.bobbywallet.domain.service;

import africa.semicolon.bobbywallet.adapter.output.repository.TransactionRepo;
import africa.semicolon.bobbywallet.application.dto.response.TransactionResponse;
import africa.semicolon.bobbywallet.application.ports.input.TransactionService;
import africa.semicolon.bobbywallet.domain.exception.TransactionNotFoundException;
import africa.semicolon.bobbywallet.domain.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo repository;
    private final ModelMapper mapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepo repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    public Transaction save(Transaction transaction) {
        Transaction history = repository.save(transaction);
        if (history == null) throw new TransactionNotFoundException("Transaction Not Found");
        return history;
    }

    @Override
    public TransactionResponse viewTransaction(Long id) {
        Transaction transaction= repository.findById(id).orElseThrow(()-> new TransactionNotFoundException("Transaction Not Found"));
        return mapper.map(transaction,TransactionResponse.class);
    }

    @Override
    public List<TransactionResponse> viewAllTransaction(Long userId) {
        List<Transaction> transactions = repository.findAllTransactionsBy(userId);
        if(transactions.isEmpty())throw new TransactionNotFoundException("No Transaction Made Yet By User With id  " + userId);
        return transactions.stream().map(transaction -> mapper.map(transaction,TransactionResponse.class)).toList();
    }
}
