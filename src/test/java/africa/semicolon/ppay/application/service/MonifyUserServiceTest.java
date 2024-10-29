package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.MonifyInitializePaymentRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.AuthorizeRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.MonifyInitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.util.Predicates.isTrue;

@SpringBootTest
@Slf4j
class MonifyUserServiceTest {
    @Autowired
    private MonifyUserService monifyUserService;

    @Test
    void authenticate() {
        MonifyAuthenticateResponse response = monifyUserService.authenticate();
        assertNotNull(response);
        log.info("AuthenticationResponse: {}", response.getResponseBody().getAccessToken());
        assertThat(response.isRequestSuccessful()).isTrue();
    }

    @Test
    public void testInitializePayment() throws JsonProcessingException {
        MonifyInitializePaymentResponse response = getMonifyInitializePaymentResponse();
        assertNotNull(response);
        log.info("PaymentResponse: {}", response.getResponseBody().getCheckoutUrl());
        assertThat(response.isRequestSuccessful()).isTrue();
    }

    private MonifyInitializePaymentResponse getMonifyInitializePaymentResponse()  {
        MonifyInitializePaymentRequest request = new MonifyInitializePaymentRequest();
        request.setAmount(BigDecimal.valueOf(10000));
        request.setPaymentDescription("For my Kid");
        request.setCustomerEmail("ajibola@gmail.com");
        request.setCustomerName("Ajibola");

        MonifyInitializePaymentResponse response = monifyUserService.initializePayment(request);
        return response;
    }

    @Test
    void testThatTransactionsCanBeVerified() throws JsonProcessingException, InterruptedException {
        MonifyInitializePaymentResponse response = getMonifyInitializePaymentResponse();
        String transactionReference = response.getResponseBody().getTransactionReference();
        log.info("PaymentResponse: {}, {}", response.getResponseBody().getCheckoutUrl(),transactionReference);

        Thread.sleep(60000);
        MonifyVerifyTransactionResponse verifyResponse = monifyUserService.verifyTransaction(transactionReference);

        assertNotNull(verifyResponse);
        log.info("Verified transaction status {}, Amount paid {}",verifyResponse.isRequestSuccessful(),verifyResponse.getResponseBody().getAmountPaid());
        assertThat(verifyResponse.getResponseBody().getPaymentStatus()).isEqualTo("PAID");

    }
    @Test
    void testThatUserCanTransferMoneyToOtherAccount()  {
        MonifyInitializeTransferResponse response = getMonifyInitializeTransferResponse();
        assertNotNull(response);
        log.info("Transfer response: {},reference: {}", response.getResponseBody().getAmount(),response.getResponseBody().getReference());
        assertThat(response.isRequestSuccessful()).isTrue();
    }

    private MonifyInitializeTransferResponse getMonifyInitializeTransferResponse() {
        MonifyInitializeTransferDto initializeTransferDto = new MonifyInitializeTransferDto();
        initializeTransferDto.setAmount(BigDecimal.valueOf(1000));
        initializeTransferDto.setDestinationAccountNumber("9027531222");
        initializeTransferDto.setNarration("Emergency");
        initializeTransferDto.setDestinationBankCode("999992");
        initializeTransferDto.setReference("BwB-" + System.currentTimeMillis());

        MonifyInitializeTransferResponse response = monifyUserService.initializeTransfer(initializeTransferDto);
        return response;
    }

    @Test
    void testThatMerchantCanAuthorizeTransfer(){
        AuthorizeRequest request = new AuthorizeRequest();
        request.setReference("BwB-1730158551431");
        request.setAuthorizationCode("450205");
        AuthorizeTransactionResponse verifyResponse = monifyUserService.authorizeTransfer(request);
        assertNotNull(verifyResponse);
        log.info("Verified transfer status {}, Amount paid {}",verifyResponse.isRequestSuccessful(),verifyResponse.getResponseBody().getAmount());
        assertThat(verifyResponse.isRequestSuccessful()).isTrue();
    }
    @Test
    void testThatVerificationCanBeVerified(){
        VerifyTransferResponse response = monifyUserService.verifyTransfer("BwB-1730158551431");

        assertNotNull(response);
        log.info("Verified transfer status {}, Amount paid {},status: {}",response.isRequestSuccessful(),response.getResponseBody().getAmount(),response.getResponseBody().getStatus());
        assertThat(response.isRequestSuccessful()).isTrue();
    }

}