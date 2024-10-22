package africa.semicolon.ppay.domain.service;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.CreateTransferRecipientDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializePaymentDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.TransferDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
class PayStackPaymentServiceTest {
    @Autowired
    private PayStackPaymentService payStackPaymentService;

    @Test
    void testThatPaymentCanBeInitialize() {
        InitializePaymentResponse response = initiateDeposit(101L);
        assertNotNull(response);
        log.info("InitializePaymentResponse{}");
        System.out.println(response.toString());
        assertThat(response.getStatus()).isEqualTo(true);

    }

    private InitializePaymentResponse initiateDeposit(Long id) {
        InitializePaymentDto initializePaymentDto = new InitializePaymentDto();
        initializePaymentDto.setId(id);
        initializePaymentDto.setAmount(BigDecimal.valueOf(1000));
        initializePaymentDto.setEmail("johndoe@example.com");

        InitializePaymentResponse response  = payStackPaymentService.initializePayment(initializePaymentDto);
        return response;
    }

    @Test
    void testThatPaymentCanBeVerified() throws Exception {
        InitializePaymentResponse response = initiateDeposit(101L);
        System.out.println(response);
        Thread.sleep(60000);
        PaymentVerificationResponse finalResponse = payStackPaymentService.paymentVerification(response.getData().getReference(), 101L);
        log.info("Final Response{}", finalResponse);

        assertThat(finalResponse.isStatus()).isEqualTo(true);
    }

    @Test
    void testThatTransferRecipientCanBeCreated() {
        CreateTransferRecipientDto createTransferRecipientDto = new CreateTransferRecipientDto(101L,"9027531222","PHILIP AJIBOLA OMIRIN", "999992");
        CreateTransferRecipientResponse response = payStackPaymentService.createTransferRecipient(createTransferRecipientDto);
        assertThat(response.getStatus()).isEqualTo(true);
    }
    @Test
    void testThatTransferCanBeInitialize() {
        initiateDeposit(101L);
        CreateTransferRecipientDto createTransferRecipientDto = new CreateTransferRecipientDto(101L,"9027531222","PHILIP AJIBOLA OMIRIN", "999992");
        CreateTransferRecipientResponse response = payStackPaymentService.createTransferRecipient(createTransferRecipientDto);
        InitializeTransferDto transferDto = new InitializeTransferDto(101L,BigDecimal.valueOf(500),response.getData().getRecipientCode());
        InitializeTransferResponse finalResponse = payStackPaymentService.transfer(transferDto);
       assertThat(finalResponse.getData().getStatus()).isEqualTo("true");
    }
}