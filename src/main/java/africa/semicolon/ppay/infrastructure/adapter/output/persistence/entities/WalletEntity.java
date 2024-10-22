package africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities;

import africa.semicolon.ppay.domain.model.DateCreated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class WalletEntity {
    @Id
    private Long id;
    @Column(length = 4)
    private String pin;
    private BigDecimal balance ;
    private DateCreated dateCreated;

    @PrePersist
    void updateData() {
        this.balance = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);
        this.pin = "0000";
    }
}
