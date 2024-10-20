package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.request.CreateUserDto;
import africa.semicolon.bobbywallet.application.dto.request.TransferDto;
import africa.semicolon.bobbywallet.application.dto.response.*;
import africa.semicolon.bobbywallet.domain.exception.UserNotFoundException;
import africa.semicolon.bobbywallet.domain.model.TransactionType;
import africa.semicolon.bobbywallet.domain.model.User;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static africa.semicolon.bobbywallet.testUtils.Helper.initiateDeposit;
import static africa.semicolon.bobbywallet.testUtils.Helper.initiateTransfer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private WalletService walletService;


    @Test
    void testThatUserCanBeFoundByUserId() {
        User user = userService.getUser(101L);
        assertThat(user).isNotNull();
        assertThat(user.getFirstname()).isEqualTo("John");
    }
    @Test
    void testThatFindingUserWithWrongIdThrowsException() {
        assertThrows(UserNotFoundException.class,()->userService.getUser(107L));
    }

    @Test
    void testThatUserCanBeCreated() {
        CreateUserDto request  = new CreateUserDto();
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setEmail("janedoe@gmail.com");
        request.setPassword("password123");
        var response = userService.createUser(request);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getFirstname()).isEqualTo("Jane");

    }

    @Test
    void testThatUserDetailsCanBeUpdated() throws JsonPointerException {
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/firstname"),new TextNode("Josh"))
        );
        JsonPatch request = new JsonPatch(operations);
        userService.updateUser(101L,request);
        User user = userService.getUser(101L);
        assertThat(user.getFirstname()).isEqualTo("Josh");
        assertNotNull(user);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(101L);
        assertThrows(UserNotFoundException.class, () -> userService.getUser(101L));
    }
    @Test
    void testThatUserThatDoesNotExistCantBeDeleted() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(107L));
    }

    @Test
    void deposit() throws Exception {
        InitializePaymentResponse response = initiateDeposit(101L,userService);
        log.info("InitializePaymentResponse{}",response);

        Thread.sleep(60000);
        WalletResponse<PaymentVerificationResponse> finalResponse = paymentService.paymentVerification(response.getData().getReference(),101L,walletService);
        assertThat(walletService.getById(101L).getBalance()).isEqualTo(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_EVEN));
        assertThat(finalResponse.getData().getStatus()).isEqualTo("true");
    }

    @Test
    void testThatUserCanTransfer() throws Exception {
        TransferDto transferDto = initiateTransfer(101L,101l);
        InitializeTransferResponse response1 = walletService.transfer(transferDto, paymentService);

        WalletResponse<TransferVerificationResponse>response = paymentService.transferVerification(response1.getData().getReference(),101L,walletService);
        assertThat(response1).isNotNull();
        assertThat(response.getData().getStatus()).isEqualTo("true");

    }

    @Test
    void testThatUserCanViewAllTransactions() {
        List<TransactionResponse> transactions = userService.viewAllTransactions(101L);
        assertThat(transactions.size()).isEqualTo(4);
    }
    @Test
    void testThatViewAllTransactionsWithInvalidIdThrowsException() {
        assertThrows(UserNotFoundException.class, () -> userService.viewAllTransactions(107L));
    }

    @Test
    void testThatUserCanViewSingleTransaction() {
        TransactionResponse transaction = userService.viewTransaction(103L);
        assertThat(transaction.getTransactionType()).isEqualTo(TransactionType.TRANSFER);
    }

    @Test
    void getUser() {
        User user = userService.getUser(101L);
        assertNotNull(user);
        assertThat(user.getFirstname()).isEqualTo("John");
    }
}