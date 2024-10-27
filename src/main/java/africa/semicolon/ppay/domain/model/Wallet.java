package africa.semicolon.ppay.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private Long id;
    private String pin;
    private BigDecimal balance ;
    private DateCreated dateCreated;

    public void deposit(BigDecimal amount){
        this.balance.add(amount);
    }
    public void transfer(BigDecimal amount){
        this.balance.subtract(amount);
    }
}
