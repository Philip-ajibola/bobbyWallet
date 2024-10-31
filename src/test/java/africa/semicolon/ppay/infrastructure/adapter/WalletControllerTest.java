package africa.semicolon.ppay.infrastructure.adapter;

import africa.semicolon.ppay.application.service.WalletService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.DepositDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.LoginRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyPaymentDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializePaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class WalletControllerTest {
    @Autowired
    private MockMvc mock;
    @Autowired
    private WalletService walletService;
    private String token ="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJrT09tRjIzdE5lSFNScnJHQnRJS3E2c2lHYUhvWEEzNDZqaTRIZVRlbHg4In0.eyJleHAiOjE3MzAwNjc4MjEsImlhdCI6MTczMDA2NzUyMSwianRpIjoiNDljYWZkNjEtNGYyMy00NmEyLWE1YTgtYTNlODA5YWNiMzhlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy93YWxsZXQiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNzUxZGQ5M2ItZGM1NS00OGI2LWJmYTItOGE1YjdlODA0MjEyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2FsbGV0X2FwaSIsInNpZCI6IjgxNDczMGFkLWQ1OTAtNDI2ZC05OGY1LWFiYmUyMzJkMTYwMSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJVU0VSUyIsImRlZmF1bHQtcm9sZXMtd2FsbGV0Il19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoicGhpbGlwIGRhbmllbCIsInByZWZlcnJlZF91c2VybmFtZSI6ImFqaWJvbGFAZ21haWwuY29tIiwiZ2l2ZW5fbmFtZSI6InBoaWxpcCIsImZhbWlseV9uYW1lIjoiZGFuaWVsIiwiZW1haWwiOiJhamlib2xhQGdtYWlsLmNvbSJ9.OCYkJ06FWd9iMld9iJhCxTQR7vBpXeHdxhhSO1OmYTkUd9lgxaTRK7YB-RR3zk0zcgRAkeEdf0Ap6drL__uJ90QlgrnsqxTGPk7jfccAEOZDLkpEMLbH-w8jFMYLHR1slj-yGTmMd8URvzAwdLAIZo-xJ55_9WMVL1HO8zbFDo40EUOcU9-UXAwaQOhrmjMRVcKn16N0ytXxha86tq47Kk333sI6usrAEE4wwGUZ8ahOO7SRKUG3ijfjToyXBrn07pa8kOpfYQERuJuUAg0TNQ3OaiVjXOmx83gXQpTN-7K7a4SzdEUhf02bw41WkjyZN9wGYWTdL7Q_XzwN5InsrQ";
    private ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testThatUserCanBeAuthenticated() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("ajibola@gmail.com");
        loginRequest.setPassword("password");
        mock.perform(post("/api/v1/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testDepositEndPoint() throws Exception {
        DepositDto depositDto = new DepositDto();
        depositDto.setId(101L);
        depositDto.setAmount(BigDecimal.valueOf(1000));
        depositDto.setEmail("johndoe@example.com");
        mock.perform(post("/api/v1/wallet/deposit")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(depositDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void testGetByIdEndPoint() throws Exception {
        mock.perform(get("/api/v1/wallet/get_by/101")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void testVerifyPaymentEndPoint() throws Exception {
        DepositDto depositDto = new DepositDto();
        depositDto.setId(101L);
        depositDto.setAmount(BigDecimal.valueOf(1000));
        depositDto.setEmail("johndoe@example.com");
        InitializePaymentResponse response  = walletService.deposit(depositDto);

        VerifyPaymentDto dto =new VerifyPaymentDto();
        dto.setAmount(BigDecimal.valueOf(1000));
        dto.setId(101L);
        dto.setReference(response.getData().getReference());
        mock.perform(post("/api/v1/wallet/verify/deposit")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
