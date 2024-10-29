package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.domain.exception.TransactionNotFoundException;
import africa.semicolon.ppay.domain.exception.WalletNotFoundException;
import africa.semicolon.ppay.domain.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletService walletService;


    @Test
    void testThatUserCanViewTransaction() {
        Transaction transaction = transactionService.viewTransaction(103L);
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(5000).setScale(2, RoundingMode.HALF_EVEN));
    }
    @Test
    void testThatUserCantViewTransactionThatDoesNotExist() {
        assertThrows(TransactionNotFoundException.class, () -> transactionService.viewTransaction(107L));
    }

    @Test
    void viewAllTransactions() {
        List<Transaction> transactions = transactionService.viewAllTransactions(101L);
        assertThat(transactions.size()).isEqualTo(4);
    }
    @Test
    public void testThatWhenTryingFindAllTransactionsForInValidUserExceptionIsThrown(){
        assertThrows(WalletNotFoundException.class,() -> transactionService.viewAllTransactions(107L));
    }
}