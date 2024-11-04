package africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter;

import africa.semicolon.ppay.application.ports.output.PayStackPaymentOutputPort;
import africa.semicolon.ppay.application.ports.output.UserOutputPort;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.domain.exception.PaymentNotFoundException;
import africa.semicolon.ppay.domain.exception.UserNotFoundException;
import africa.semicolon.ppay.domain.model.Payment;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.service.UserService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.CreateTransferRecipientDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializePaymentDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;
import africa.semicolon.ppay.infrastructure.adapter.output.mappers.EntityMappers;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.PaymentEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.PayStackPaymentEntityRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.math.BigDecimal;

import static africa.semicolon.ppay.domain.constant.PayStackAPIConstant.*;
import static africa.semicolon.ppay.infrastructure.utils.HelperClass.bufferReader;

public class PayStackPaymentPersistenceAdapter implements PayStackPaymentOutputPort {
    private final PayStackPaymentEntityRepo payStackPaymentEntityRepo;
    @Value("${paystack.api.key}")
    private String PAY_STACK_SECRET_KEY;
    private final UserOutputPort userOutputPort;

    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient client = HttpClientBuilder.create().build();

    public PayStackPaymentPersistenceAdapter(PayStackPaymentEntityRepo payStackPaymentEntityRepo, UserOutputPort userOutputPort) {
        this.payStackPaymentEntityRepo = payStackPaymentEntityRepo;
        this.userOutputPort = userOutputPort;

    }

    @Override
    public Payment savePayment(Payment payment) {
        PaymentEntity entity = EntityMappers.INSTANCE.toEntity(payment);
        entity = payStackPaymentEntityRepo.save(entity);
        return EntityMappers.INSTANCE.toModel(entity);
    }

    @Override
    public Payment findPaymentById(Long id) {
        PaymentEntity entity = payStackPaymentEntityRepo.findById(id).orElseThrow(()->new PaymentNotFoundException("Payment With "+ id + " Not Found"));
        return EntityMappers.INSTANCE.toModel(entity);
    }



    @Override
    @Transactional
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
        if(!userOutputPort.existsById(initializePaymentDto.getId()))throw new UserNotFoundException(String.format("User With Id %s Not Found", initializePaymentDto.getId()));
        InitializePaymentResponse initializePaymentResponse = null;
        initializePaymentDto.setAmount(initializePaymentDto.getAmount().multiply(BigDecimal.valueOf(100)));
        try {
            StringBuilder result = checkRequest(initializePaymentDto,PAYSTACK_INITIALIZE_PAY);
            initializePaymentResponse = objectMapper. readValue(result.toString(), InitializePaymentResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return initializePaymentResponse;
    }


    @Override
    @Transactional
    public PaymentVerificationResponse paymentVerification(String reference, Long id)  {
        PaymentVerificationResponse paymentVerificationResponse ;
        PaymentEntity paymentPaystack = null;
        try{
            HttpGet request = createHeader(PAYSTACK_VERIFY,reference);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {
                result = bufferReader(response, result);
            } else {
                result =  bufferReader(response, result);
                throw new PPayWalletException(result.toString());
            }
            paymentVerificationResponse = objectMapper.readValue(result.toString(), PaymentVerificationResponse.class);


            if( paymentVerificationResponse == null || !paymentVerificationResponse.isStatus()) {
                throw new PPayWalletException("An error occur: " + paymentVerificationResponse.getMessage());
            } else if (paymentVerificationResponse.isStatus()) {

                paymentPaystack = createPaymentModel(id, paymentVerificationResponse);
            }
        } catch (Exception ex) {
            throw new PPayWalletException(ex.getMessage());
        }
        payStackPaymentEntityRepo.save(paymentPaystack);
        return paymentVerificationResponse;
    }




    private HttpGet createHeader(String url,String reference) {
        HttpGet request = new HttpGet(url + reference);
        request.addHeader("Content-type", "application/json");
        request.addHeader("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);
        return request;
    }

    @Override
    @Transactional
    public TransferVerificationResponse transferVerification(String reference, Long id){
        TransferVerificationResponse transferVerificationResponse ;
        PaymentEntity paymentPaystack = null;
        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = createHeader(PAYSTACK_VERIFY_TRANSFER,reference);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {

                bufferReader(response, result);
            } else {
                bufferReader(response, result);
                System.out.println(result);
                throw new PPayWalletException("Paystack is unable to verify payment at the moment");
            }
            transferVerificationResponse = objectMapper.readValue(result.toString(), TransferVerificationResponse.class);

            if( transferVerificationResponse == null || !transferVerificationResponse.isStatus()) {
                throw new Exception("An error");
            } else if (transferVerificationResponse.isStatus()) {
                paymentPaystack = createPaymentModel(id, transferVerificationResponse);
            }
        } catch (Exception ex) {
            throw new PPayWalletException(ex.getMessage());
        }
        payStackPaymentEntityRepo.save(paymentPaystack);
        return transferVerificationResponse;
    }

    private PaymentEntity createPaymentModel(Long id, PaymentVerificationResponse paymentVerificationResponse) {
        PaymentEntity paymentPaystack;
        User appUser = userOutputPort.getUserById(id);

        paymentPaystack = PaymentEntity.builder()
                .user(EntityMappers.INSTANCE.toEntity(appUser))
                .reference(paymentVerificationResponse.getData().getReference())
                .amount(paymentVerificationResponse.getData().getAmount())
                .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                .paidAt(paymentVerificationResponse.getData().getPaidAt())
                .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                .channel(paymentVerificationResponse.getData().getChannel())
                .currency(paymentVerificationResponse.getData().getCurrency())
                .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                .build();
        return paymentPaystack;
    }
    private PaymentEntity createPaymentModel(Long id, TransferVerificationResponse transferVerificationResponse) {
        PaymentEntity paymentPaystack;
        User appUser = userOutputPort.getUserById(id);

        paymentPaystack = PaymentEntity.builder()
                .user(EntityMappers.INSTANCE.toEntity(appUser))
                .reference(transferVerificationResponse.getData().getReference())
                .amount(transferVerificationResponse.getData().getAmount())
                .gatewayResponse(transferVerificationResponse.getData().getGatewayResponse())
                .paidAt(transferVerificationResponse.getData().getTransferredAt())
                .createdAt(transferVerificationResponse.getData().getCreatedAt())
                .currency(transferVerificationResponse.getData().getCurrency())
                .recipient(transferVerificationResponse.getData().getRecipient())
                .build();
        return paymentPaystack;
    }

    @Override
    @Transactional
    public CreateTransferRecipientResponse createTransferRecipient(CreateTransferRecipientDto createTransferRecipientDto) {
        if(!userOutputPort.existsById(createTransferRecipientDto.getId()))throw new UserNotFoundException(String.format("User with Id %s Not Found",createTransferRecipientDto.getId()));
        CreateTransferRecipientResponse createTransferRecipientResponse = null;
        try {
            StringBuilder result = checkRequest(createTransferRecipientDto,PAYSTACK_CREATE_TRANSFER_RECIPIENT);
            createTransferRecipientResponse = objectMapper. readValue(result.toString(), CreateTransferRecipientResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return createTransferRecipientResponse;
    }

    @Override
    public InitializeTransferResponse transfer(InitializeTransferDto request) {
        InitializeTransferResponse initializeTransferResponse = null;
        request.setAmount(request.getAmount().multiply(BigDecimal.valueOf(100)));
        try {
            StringBuilder result = checkRequest(request,PAYSTACK_INITIALIZE_TRANSFER);
            initializeTransferResponse = objectMapper. readValue(result.toString(), InitializeTransferResponse.class);

        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return initializeTransferResponse;
    }

    private StringBuilder checkRequest(Object request,String url) throws IOException {
        validateObject(request);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(request));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setEntity(postingString);
        post.addHeader("Content-type", "application/json");
        post.addHeader("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);
        StringBuilder result = new StringBuilder();
        HttpResponse response = client.execute(post);
        if (response.getStatusLine(). getStatusCode() == STATUS_CODE_CREATED ||response.getStatusLine(). getStatusCode() ==  STATUS_CODE_OK) {

            result = bufferReader(response, result);
        } else {
            result = bufferReader(response, result);
            throw new PPayWalletException(result.toString());
        }
        return result;
    }

    private static void validateObject(Object request) {
        if (!request.getClass().equals(InitializePaymentDto.class) &&
                !request.getClass().equals(InitializeTransferDto.class) &&
                !request.getClass().equals(CreateTransferRecipientDto.class)) {
            throw new PPayWalletException("Invalid Request");
        }
    }
}
