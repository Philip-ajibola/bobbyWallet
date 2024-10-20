package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.request.InitializePaymentDto;
import africa.semicolon.bobbywallet.application.dto.request.TransferDto;
import africa.semicolon.bobbywallet.application.dto.response.*;
import africa.semicolon.bobbywallet.domain.exception.WalletNotFoundException;
import africa.semicolon.bobbywallet.domain.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@Slf4j
class WalletServiceTest {
    @Autowired
    private  WalletService walletService;
    @Autowired
    private PaymentService paymentService;
    @Test
    void testThatWalletCanBeCreated() {
       Wallet response = walletService.createWallet(103L);
       assertThat(response.getId()).isNotNull();
    }

    @Test
    void testThatUserCanInitiateDeposit() {
        InitializePaymentResponse response = initiateDeposit(101L);
        log.info("PayStackResponse{}", response.getData());
        System.out.println(response.getData().toString());
        assertThat(response.getData().getReference()).isNotNull();
        assertThat(response.getData().getAuthorizationUrl()).isNotNull();
    }
    @Test
    void testThatPaymentCanBeVerified() throws Exception {
        InitializePaymentResponse response = initiateDeposit(101L);
        log.info("PayStackResponse{}", response.toString());
        System.out.println(response.toString());
        System.out.flush();
//      i want this to run 2 minutes after the initiate deposit has worked
        Thread.sleep(60000);
        WalletResponse<PaymentVerificationResponse> finalResponse = paymentService.paymentVerification(response.getData().getReference(),101L,walletService);
        System.out.println(walletService.getById(101L).getBalance());
        assertThat(walletService.getById(101L).getBalance()).isEqualTo(BigDecimal.valueOf(1000).setScale(2,RoundingMode.HALF_EVEN));
        assertThat(finalResponse.getData().getStatus()).isEqualTo("true");
    }
    @Test
    void testThatWhenUserEnterWrongIdDepositCantBeMade(){
        assertThrows(WalletNotFoundException.class,()->initiateDeposit(110L));
    }
 @Test
    void testThatWhenUserEnterWrongIdTransferCantBeMade(){
     TransferDto transferDto = initiateTransfer(101L,107l);
     assertThrows(WalletNotFoundException.class,()->walletService.transfer(transferDto, paymentService));
    }

    @Test
    void testThatWhenWalletWithWrongIdIsRequested_ExceptionIsThrown(){
        assertThrows(WalletNotFoundException.class,()->walletService.getById(110L));
    }

    @Test
    void testThatUserCanTransfer() {
        TransferDto transferDto = initiateTransfer(101L,101l);
        InitializeTransferResponse response1 = walletService.transfer(transferDto, paymentService);
        assertThat(response1).isNotNull();
    }

    private TransferDto initiateTransfer(Long id,Long transferId ) {
        initiateDeposit(id);
        TransferDto transferDto = new TransferDto();
        transferDto.setAmount(BigDecimal.valueOf(500));
        transferDto.setId(transferId);
        transferDto.setName("John Doe");
        transferDto.setBankCode("999992");
        transferDto.setAccountNumber("9027531222");
        return transferDto;
    }

    private InitializePaymentResponse initiateDeposit(Long id) {
        InitializePaymentDto initializePaymentDto = new InitializePaymentDto();
        initializePaymentDto.setId(id);
        initializePaymentDto.setAmount(BigDecimal.valueOf(1000));
        initializePaymentDto.setEmail("johndoe@example.com");
        InitializePaymentResponse response = walletService.deposit(initializePaymentDto,paymentService);
        return response;
    }

    @Test
    void testThatUserCanGetTheirBalance() {
        initiateDeposit(101L);
        WalletResponse response = walletService.getBalance(101L);
        assertThat(response.getBalance()).isEqualTo(BigDecimal.valueOf(100000).setScale(2, RoundingMode.HALF_UP));
    }
}