package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class VerifyUserIdentityDto {
    @NotBlank(message = "nin must not be null or empty")
    private String nin;
    private MultipartFile imageFile;
}
