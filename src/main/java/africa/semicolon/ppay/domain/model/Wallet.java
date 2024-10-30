package africa.semicolon.ppay.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private Long id;
    private String pin;
    private BigDecimal balance ;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;
    public void deposit(BigDecimal amount){
        this.balance.add(amount);
    }
    public void transfer(BigDecimal amount){
        this.balance.subtract(amount);
    }
}
