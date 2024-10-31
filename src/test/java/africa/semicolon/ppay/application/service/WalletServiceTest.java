package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.domain.exception.WalletNotFoundException;
import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.*;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.CreateTransferRecipientResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializePaymentResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializeTransferResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.PaymentVerificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@Slf4j
class WalletServiceTest {
    @Autowired
    private WalletService walletService;
    @Test
    void testThatWalletCanBeFoundUsingId() {
        Wallet wallet = walletService.findById(101L);
        assertNotNull(wallet);
        assertThat(wallet.getBalance()).isEqualTo(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
    }
    @Test
    void testThatFindingThatWithInvalidIdThrowsException() {
        assertThrows(WalletNotFoundException.class,()->walletService.findById(103L));
    }
    @Test
    void testThatPaymentCanBeInitialize() {
        InitializePaymentResponse response = initiateDeposit(101L);
        assertNotNull(response);
        log.info("InitializePaymentResponse{}");
        System.out.println(response.toString());
        assertThat(response.getStatus()).isEqualTo(true);

    }

    private InitializePaymentResponse initiateDeposit(Long id) {
        DepositDto depositDto = new DepositDto();
        depositDto.setId(id);
        depositDto.setAmount(BigDecimal.valueOf(1000));
        depositDto.setEmail("johndoe@example.com");

        InitializePaymentResponse response  = walletService.deposit(depositDto);
        return response;
    }

    @Test
    void testThatPaymentCanBeVerified() throws Exception {
        InitializePaymentResponse response = initiateDeposit(101L);
        ;
        Thread.sleep(60000);
        VerifyPaymentDto dto =new VerifyPaymentDto();
        dto.setAmount(BigDecimal.valueOf(1000));
        dto.setId(101L);
        dto.setReference(response.getData().getReference());
        Wallet finalResponse = walletService.verifyPayment(dto);
        log.info("Final Response{}", finalResponse);

        assertThat(finalResponse.getBalance()).isEqualTo(BigDecimal.valueOf(1000));
    }


    @Test
    void testThatTransferCanBeInitialize() {
        initiateDeposit(101L);
        TransferDto dto = new TransferDto();
        dto.setId(101L);
        dto.setAmount(BigDecimal.valueOf(500));
        dto.setAccountNumber("9027531222");
        dto.setBankCode("999992");
        dto.setPin("0000");
        dto.setName("Philip Ajibola Omirin");
        InitializeTransferResponse response = walletService.transfer(dto);
        assertNotNull(response);
        assertThat(response.isStatus()).isTrue();
    }

}