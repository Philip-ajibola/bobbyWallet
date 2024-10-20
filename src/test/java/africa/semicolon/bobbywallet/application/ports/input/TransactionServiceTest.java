package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.response.TransactionResponse;
import africa.semicolon.bobbywallet.domain.exception.TransactionNotFoundException;
import africa.semicolon.bobbywallet.domain.model.TransactionType;
import africa.semicolon.bobbywallet.domain.model.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    void testThatUserCanViewSingleTransaction() {
        TransactionResponse response = transactionService.viewTransaction(103L);
        assertThat(response.getTransactionType()).isEqualTo(TransactionType.TRANSFER);
    }
    @Test
    void testThatUserCantViewTransactionThatDoesNotExist() {
        assertThrows(TransactionNotFoundException.class,()->transactionService.viewTransaction(107L));
    }
    @Test
    void testThatTransactionWithWrongUserIdCantBeFound() {
        assertThrows(TransactionNotFoundException.class,()->transactionService.viewAllTransaction(107L));
    }

    @Test
    void testThatUserCanViewAllTransactions() {
        List<TransactionResponse> responses = transactionService.viewAllTransaction(101L);
        assertThat(responses).hasSize(4);
    }
}