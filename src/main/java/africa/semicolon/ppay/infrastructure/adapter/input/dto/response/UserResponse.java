package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;

import africa.semicolon.ppay.domain.model.DateCreated;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private DateCreated dateCreated;
    private WalletResponse response;
}
