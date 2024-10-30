package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;


import africa.semicolon.ppay.domain.model.DateCreated;
import africa.semicolon.ppay.domain.model.TransactionStatus;
import africa.semicolon.ppay.domain.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private Long id;
    private TransactionStatus status;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime dateCreated;
}
