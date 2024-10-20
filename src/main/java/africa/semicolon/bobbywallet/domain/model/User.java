package africa.semicolon.bobbywallet.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactionHistories;
   private DateCreated dateCreated;
}
