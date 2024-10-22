package africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities;

import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;
    private String reference;
    private String channel;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String currency ;
    private String ipAddress;
    private String recipient;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

}
