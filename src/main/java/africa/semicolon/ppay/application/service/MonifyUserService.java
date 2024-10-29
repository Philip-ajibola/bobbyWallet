package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.application.ports.input.monifyUseCase.AuthorizeTransferUseCase;
import africa.semicolon.ppay.application.ports.input.monifyUseCase.InitializePaymentUseCase;
import africa.semicolon.ppay.application.ports.input.monifyUseCase.TransferUseCase;
import africa.semicolon.ppay.application.ports.input.monifyUseCase.VerifyPaymentUseCase;
import africa.semicolon.ppay.domain.exception.InvalidUserCredentials;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.MonifyInitializePaymentRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.AuthorizeRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.MonifyInitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;

import static africa.semicolon.ppay.domain.constant.MonifyApiConstant.*;

@Slf4j
public class MonifyUserService  implements InitializePaymentUseCase, VerifyPaymentUseCase, TransferUseCase, AuthorizeTransferUseCase {
    @Value("${monify.api.key}")
    private String MONIFY_API_KEY;
    @Value("${monify.api.secret}")
    private String MONIFY_API_SECRET;
    @Value("${monify.contract.key}")
    private String contractCode;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;
    @Value("${monify.account-number}")
    private  String accountNumber;

    public MonifyUserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public MonifyAuthenticateResponse authenticate(){
        String credentials = MONIFY_API_KEY + ":" + MONIFY_API_SECRET;
        String encodedAuthorizationBearer = Base64.getEncoder().encodeToString(credentials.getBytes());
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(LOGIN_URL);
        postRequest.setHeader("Authorization", "Basic " + encodedAuthorizationBearer);
        try{
            HttpResponse response =  client.execute(postRequest);
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);

            return objectMapper.readValue(jsonResponse,MonifyAuthenticateResponse.class);
        }catch (IOException e){
            throw new PPayWalletException(e.getMessage());
        }
    }
    public MonifyInitializePaymentResponse initializePayment(MonifyInitializePaymentRequest request)  {

        String token = authenticate().getResponseBody().getAccessToken();
        request.setContractCode(contractCode);
        try {
            return webClient.post()
                    .uri(INITIATE_TRANSACTION)
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(request))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response ->
                            Mono.error(new InvalidUserCredentials("Invalid User Credentials"))
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new PPayWalletException("Couldn't Verify Indentity Due to Internal Server Error"))
                    )
                    .bodyToMono(MonifyInitializePaymentResponse.class)
                    .doOnError(error -> new PPayWalletException("Error during transaction verification: " + error.getMessage()))
                    .block();
        }catch (JsonProcessingException e){
            throw new PPayWalletException("Error: " + e.getMessage());
        }
    }

    public MonifyVerifyTransactionResponse verifyTransaction(String reference){
        String token = authenticate().getResponseBody().getAccessToken();

        return webClient.get()
                .uri(VERIFY_TRANSACTION + reference)
                .header("Authorization", "Bearer " +token)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response->
                Mono.error(new InvalidUserCredentials("Invalid Transaction reference"))
        )
                .onStatus(HttpStatusCode::is5xxServerError, response->
                        Mono.error(new PPayWalletException("Couldn't Verify Indentity Due to Internal Server Error"))
                )

                .bodyToMono(MonifyVerifyTransactionResponse.class)
                .flatMap(response -> {
                    if (response.isRequestSuccessful()) {
                        return Mono.just(response);
                    } else {
                        return Mono.error(new PPayWalletException("Payment Verification failed"));
                    }
                })
                .doOnError(error -> new PPayWalletException("Error during transaction verification: " + error.getMessage()))
                .block();
    }


    @Override
    public MonifyInitializeTransferResponse initializeTransfer(MonifyInitializeTransferDto dto)  {
        String token = authenticate().getResponseBody().getAccessToken();
        dto.setSourceAccountNumber(accountNumber);
        try {

          return   webClient.post()
                    .uri(INITIATE_TRANSFER)
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(dto))
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError, response ->
                                    Mono.error(new PPayWalletException("Transfer can't be made an error occurred due to invalid params")
                                    ))
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new PPayWalletException("Couldn't Verify Indentity Due to Internal Server Error"))
                    )
                    .bodyToMono(MonifyInitializeTransferResponse.class).block();

        }catch (JsonProcessingException e){
            throw new PPayWalletException("Error occurred while parsing JSON" + e);
        }
    }
    public AuthorizeTransactionResponse authorizeTransfer(AuthorizeRequest request){

        String token = authenticate().getResponseBody().getAccessToken();
        try {
            return webClient.post()
                    .uri(AUTHORIZE_TRANSFER)
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(objectMapper.writeValueAsString(request))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response ->
                            Mono.error(new InvalidUserCredentials("Error Occur due to invalid params"))
                    )
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new PPayWalletException("Couldn't Verify Indentity Due to Internal Server Error"))
                    )
                    .bodyToMono(AuthorizeTransactionResponse.class)
                    .doOnError(error -> new PPayWalletException("Error occurred while authorizing transfer: " + error.getMessage()))
                    .block();
        }catch (JsonProcessingException e){
            throw new PPayWalletException("Error occurred while parsing JSON" + e);
        }
    }
    public VerifyTransferResponse verifyTransfer(String reference){
        String token = authenticate().getResponseBody().getAccessToken();
        return webClient.get()
                .uri(VERIFY_TRANSFER + reference)
                .header("Authorization", "Bearer "+token)
                .header("Accept", "application/json")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidUserCredentials("Error occurred due to invalid Transaction Reference"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new PPayWalletException("Couldn't Verify Indentity Due to Internal Server Error"))
                ).bodyToMono(VerifyTransferResponse.class)
                .flatMap(response -> {
                    if (response.isRequestSuccessful()) {
                        return Mono.just(response);
                    } else {
                        return Mono.error(new PPayWalletException(" Transfer Verification failed"));
                    }
                })
                .doOnError(error -> new PPayWalletException("Error occurred while verifying transfer: " + error.getMessage()))
                .block();

    }

}
