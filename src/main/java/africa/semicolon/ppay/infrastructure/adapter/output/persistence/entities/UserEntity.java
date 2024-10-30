package africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities;

import africa.semicolon.ppay.domain.model.DateCreated;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.Wallet;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

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
    private String role;
    private String keyCloakId;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private WalletEntity wallet;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionHistories;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;

    @PrePersist
    public void setDateCreated() {
        dateCreated = now();
    }


}
