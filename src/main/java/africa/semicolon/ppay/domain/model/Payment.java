package africa.semicolon.ppay.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long id;
    private User user;
    private String reference;
    private String channel;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String currency ;
    private String ipAddress;
    private String recipient;
    private Date createdOn;
}
