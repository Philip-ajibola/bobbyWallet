package africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities;

import africa.semicolon.ppay.domain.model.DateCreated;
import africa.semicolon.ppay.domain.model.TransactionStatus;
import africa.semicolon.ppay.domain.model.TransactionType;
import africa.semicolon.ppay.domain.model.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @ManyToOne
    private WalletEntity wallet;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    private DateCreated dateCreated;
}
