package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;


import africa.semicolon.ppay.domain.model.DateCreated;
import africa.semicolon.ppay.domain.model.TransactionStatus;
import africa.semicolon.ppay.domain.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private TransactionStatus status;
    private TransactionType transactionType;
    private BigDecimal amount;
    private DateCreated dateCreated;
}
