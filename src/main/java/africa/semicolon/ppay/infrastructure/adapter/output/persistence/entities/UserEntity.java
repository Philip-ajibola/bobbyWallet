package africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities;

import africa.semicolon.ppay.domain.model.DateCreated;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private String keyCloakId;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private WalletEntity wallet;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionHistories;
    private DateCreated dateCreated;


}
