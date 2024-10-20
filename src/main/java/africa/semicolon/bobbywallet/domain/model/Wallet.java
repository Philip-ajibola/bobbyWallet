package africa.semicolon.bobbywallet.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @NonNull
    private Long id;
    private BigDecimal balance ;
    private DateCreated dateCreated;

    @PrePersist
    void updateDateCreated() {
        this.balance = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);
    }
}
