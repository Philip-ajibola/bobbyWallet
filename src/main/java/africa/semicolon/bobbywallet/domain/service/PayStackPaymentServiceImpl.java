package africa.semicolon.bobbywallet.domain.service;

import africa.semicolon.bobbywallet.adapter.output.repository.PaymentRepo;
import africa.semicolon.bobbywallet.adapter.output.repository.TransactionRepo;
import africa.semicolon.bobbywallet.application.dto.request.CreateTransferRecipientDto;
import africa.semicolon.bobbywallet.application.dto.request.InitializePaymentDto;
import africa.semicolon.bobbywallet.application.dto.request.InitializeTransferDto;
import africa.semicolon.bobbywallet.application.dto.response.*;
import africa.semicolon.bobbywallet.application.ports.input.PaymentService;
import africa.semicolon.bobbywallet.application.ports.input.UserService;
import africa.semicolon.bobbywallet.application.ports.input.WalletService;
import africa.semicolon.bobbywallet.domain.exception.BobbyWalletException;
import africa.semicolon.bobbywallet.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;

import static africa.semicolon.bobbywallet.domain.constant.PayStackAPIConstant.*;

@Service
public class PayStackPaymentServiceImpl implements PaymentService {
    @Value("${paystack.api.key}")
    private String PAY_STACK_SECRET_KEY;
    private final UserService userService;
    private final PaymentRepo payStackRepository;
    private final TransactionRepo transactionRepo;
    private final ModelMapper mapper;
    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient client = HttpClientBuilder.create().build();
    @Autowired
    public PayStackPaymentServiceImpl(UserService userService, PaymentRepo payStackRepository, TransactionRepo transactionRepo, ModelMapper mapper) {
        this.userService = userService;
        this.payStackRepository = payStackRepository;
        this.transactionRepo = transactionRepo;
        this.mapper = mapper;
    }
    @Override
    @Transactional
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
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
    private WalletResponse deposit(BigDecimal amount, Long id, @Autowired WalletService service) {
        Wallet wallet = service.getById(id);
        wallet.setBalance(wallet.getBalance().add(amount.divide(BigDecimal.valueOf(100))));
        wallet = service.save(wallet);
        return  mapper.map(wallet, WalletResponse.class);
    }


    @Override
    @Transactional
    public WalletResponse paymentVerification(String reference, Long id, @Autowired WalletService service) throws Exception {
        PaymentVerificationResponse paymentVerificationResponse ;
        Payment paymentPaystack = null;
        try{
            HttpGet request = createHeader(PAYSTACK_VERIFY,reference);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new BobbyWalletException("Paystack is unable to verify payment at the moment");
            }
            paymentVerificationResponse = objectMapper.readValue(result.toString(), PaymentVerificationResponse.class);
            deposit(paymentVerificationResponse.getData().getAmount(),id,service);
                Transaction transaction = new Transaction();
                transaction.setAmount(paymentVerificationResponse.getData().getAmount());
                transaction.setWallet(service.getById(id));
                transaction.setTransactionType(TransactionType.DEPOSIT);
                transaction.setStatus(paymentVerificationResponse.getStatus().equals("true")? TransactionStatus.SUCCESSFUL : TransactionStatus.FAILED);
                transactionRepo.save(transaction);

            if( paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse.getStatus().equals("true")) {

                paymentPaystack = createPaymentModel(id, paymentVerificationResponse);
            }
        } catch (Exception ex) {
            throw new BobbyWalletException(ex.getMessage());
        }
        System.out.println(paymentPaystack.getAmount());
        payStackRepository.save(paymentPaystack);
        WalletResponse<PaymentVerificationResponse> response = new WalletResponse<>(id,service.getById(id).getBalance(),paymentVerificationResponse);
        return response;
    }


    private HttpGet createHeader(String url,String reference) {
        HttpGet request = new HttpGet(url + reference);
        request.addHeader("Content-type", "application/json");
        request.addHeader("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);
        return request;
    }

    private WalletResponse transfer(BigDecimal amount,Long id,@Autowired WalletService service ){
        Wallet wallet = service.getById(id);
        wallet.setBalance(wallet.getBalance().subtract(amount.divide(BigDecimal.valueOf(100))));
        wallet = service.save(wallet);
        return  mapper.map(wallet, WalletResponse.class);
    }
    @Override
    @Transactional
    public WalletResponse transferVerification(String reference, Long id, @Autowired WalletService service) throws Exception {
        TransferVerificationResponse transferVerificationResponse ;
        Payment paymentPaystack = null;
        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = createHeader(PAYSTACK_VERIFY_TRANSFER,reference);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result);
                throw new BobbyWalletException("Paystack is unable to verify payment at the moment");
            }
            transferVerificationResponse = objectMapper.readValue(result.toString(), TransferVerificationResponse.class);
            transfer(transferVerificationResponse.getData().getAmount(),id,service);
                Transaction transaction = new Transaction();
                transaction.setAmount(transferVerificationResponse.getData().getAmount());
                transaction.setWallet(service.getById(id));
                transaction.setTransactionType(TransactionType.TRANSFER);
                transaction.setStatus(transferVerificationResponse.getStatus().equals("true")? TransactionStatus.SUCCESSFUL : TransactionStatus.FAILED);
                transactionRepo.save(transaction);

            if( transferVerificationResponse == null || transferVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (transferVerificationResponse.getStatus().equals("true")) {
                paymentPaystack = createPaymentModel(id, transferVerificationResponse);
            }
        } catch (Exception ex) {
            throw new BobbyWalletException(ex.getMessage());
        }
        payStackRepository.save(paymentPaystack); WalletResponse<TransferVerificationResponse> response = new WalletResponse<>(id,service.getById(id).getBalance(),transferVerificationResponse);
        return response;
    }

    private Payment createPaymentModel(Long id, PaymentVerificationResponse paymentVerificationResponse) {
        Payment paymentPaystack;
        User appUser = userService.getUser(id);

        paymentPaystack = Payment.builder()
                .user(appUser)
                .reference(paymentVerificationResponse.getData().getReference())
                .amount(paymentVerificationResponse.getData().getAmount())
                .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                .paidAt(paymentVerificationResponse.getData().getPaidAt())
                .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                .channel(paymentVerificationResponse.getData().getChannel())
                .currency(paymentVerificationResponse.getData().getCurrency())
                .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                .createdOn(new Date())
                .build();
        return paymentPaystack;
    }
    private Payment createPaymentModel(Long id, TransferVerificationResponse transferVerificationResponse) {
        Payment paymentPaystack;
        User appUser = userService.getUser(id);

        paymentPaystack = Payment.builder()
                .user(appUser)
                .reference(transferVerificationResponse.getData().getReference())
                .amount(transferVerificationResponse.getData().getAmount())
                .gatewayResponse(transferVerificationResponse.getData().getGatewayResponse())
                .paidAt(transferVerificationResponse.getData().getTransferredAt())
                .createdAt(transferVerificationResponse.getData().getCreatedAt())
                .currency(transferVerificationResponse.getData().getCurrency())
                .recipient(transferVerificationResponse.getData().getRecipient())
                .createdOn(new Date())
                .build();
        return paymentPaystack;
    }

    @Override
    @Transactional
    public CreateTransferRecipientResponse createTransferRecipient(CreateTransferRecipientDto createTransferRecipientDto) {
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

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } else {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result);
            throw new BobbyWalletException("Paystack is unable to initialize Transfer at the moment");
        }
        return result;
    }

    private static void validateObject(Object request) {
        if (!request.getClass().equals(InitializePaymentDto.class) &&
                !request.getClass().equals(InitializeTransferDto.class) &&
                !request.getClass().equals(CreateTransferRecipientDto.class)) {
            throw new BobbyWalletException("Invalid Request");
        }
    }
}
