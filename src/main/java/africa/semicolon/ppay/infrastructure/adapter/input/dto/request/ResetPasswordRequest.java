package africa.semicolon.ppay.infrastructure.adapter.input.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @NotBlank(message = "id must be null or empty")
    private Long id;
    @NotBlank(message = "oldPassword must be null or empty")
    private String oldPassword;
    @NotBlank(message = "newPassword must be null or empty")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String newPassword;
}
