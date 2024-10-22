package africa.semicolon.ppay.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private Wallet wallet;
    private List<Transaction> transactionHistories;
   private DateCreated dateCreated;
}
