package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class VerifyUserIdentityDto {
    private String nin;
    private MultipartFile imageFile;
}
