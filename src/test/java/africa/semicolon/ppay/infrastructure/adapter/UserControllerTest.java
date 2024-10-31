package africa.semicolon.ppay.infrastructure.adapter;


import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.ChangePinRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    @SpringBootTest
    @AutoConfigureMockMvc
    @Sql(scripts = {"/db/data.sql"})
   public class UserControllerTest {
        @Autowired
        private MockMvc mock;
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
        void testThatUseCanBegottenWithUserId() throws Exception {
            mock.perform(get("/api/v1/users/find_by_id/101")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization","Bearer " + token)
                    )
                    .andExpect(status().isOk()).
                    andDo(print())
            ;
        }
        @Test
        void testThatUserCanBeGottenWithEmails() throws Exception {
            mock.perform(get("/api/v1/users/find_by_email/ajibola@gmail.com")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization","Bearer " + token)
                    )
                    .andExpect(status().isOk()).
                    andDo(print())
            ;
        }
        @Test
        void testThatUserCanBeUpdated() throws Exception {
            String jsonPatch = "[{ \"op\": \"replace\", \"path\": \"/lastname\", \"value\": \"Michael\" }]";
            mock.perform(patch("/api/v1/users/update/101")
                            .header("Authorization","Bearer " + token)
                            .contentType("application/json-patch+json")
                            .content(jsonPatch)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }
        @Test
        void deleteUser() throws Exception {
            mock.perform(get("/api/v1/users/delete/101")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization","Bearer " + token)
                    )
                    .andExpect(status().isOk()).
                    andDo(print())
            ;
        }
        @Test
        void changePin() throws Exception {
            ChangePinRequest changePinRequest = new ChangePinRequest();
            changePinRequest.setPassword("password");
            changePinRequest.setId(101L);
            changePinRequest.setNewPin("1234");
            mock.perform(patch("/api/v1/users/change_pin")
                            .contentType(APPLICATION_JSON)
                            .header("Authorization","Bearer " + token)
                            .content(objectMapper.writeValueAsBytes(changePinRequest))
                    )
                    .andExpect(status().isOk()).
                    andDo(print())
            ;
        }
}
