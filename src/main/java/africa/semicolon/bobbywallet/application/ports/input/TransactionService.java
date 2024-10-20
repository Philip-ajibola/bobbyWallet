package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.response.TransactionResponse;
import africa.semicolon.bobbywallet.domain.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);
    TransactionResponse viewTransaction(Long id);
    List<TransactionResponse> viewAllTransaction(Long id);
}
