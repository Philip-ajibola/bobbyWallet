package africa.semicolon.ppay.application.ports;

import africa.semicolon.ppay.application.ports.output.PremblyOutputPort;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyUserIdentityDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class PremblyIdentityVerificationOutputPortTest {
    @Autowired
    private PremblyOutputPort premblyIdentityVerificationService;
    @Value("${prembly.nin}")
    private String nin;

    @Test
    void verifyUserIdentity() {
        Path path = Paths.get("C:\\Users\\Dell\\Desktop\\bobbyWallet\\src\\main\\resources\\static\\images\\WhatsApp Image 2024-10-17 at 13.34.26_5e404566.jpg");
        try(var inputStream= Files.newInputStream(path)) {
            VerifyUserIdentityDto verifyRequest = new VerifyUserIdentityDto();
            MultipartFile file = new MockMultipartFile("image",inputStream);
            verifyRequest.setImageFile(file);
            verifyRequest.setNin(nin);
            var response = premblyIdentityVerificationService.verifyUserIdentity(verifyRequest);
            log.info("Response {}", response);
            assertThat(response).isNotNull();
        }catch (IOException e){
            assertThat(e).isNull();
        }
    }
}