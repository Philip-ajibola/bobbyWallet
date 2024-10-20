package africa.semicolon.bobbywallet.application.dto.response;

import africa.semicolon.bobbywallet.domain.model.DateCreated;
import africa.semicolon.bobbywallet.domain.model.TransactionStatus;
import africa.semicolon.bobbywallet.domain.model.TransactionType;
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
