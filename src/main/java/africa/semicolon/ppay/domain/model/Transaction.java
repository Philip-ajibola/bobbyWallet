package africa.semicolon.ppay.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class Transaction {
    private Long id;
    private TransactionStatus status;
    private Wallet wallet;
    private TransactionType transactionType;
    private BigDecimal amount;
    private DateCreated dateCreated;
}
