package africa.semicolon.ppay.application.ports.input.transactionUseCase;

import africa.semicolon.ppay.domain.model.Transaction;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface GetAllTransactionUseCase {
    List<Transaction> getAllTransaction();
}
