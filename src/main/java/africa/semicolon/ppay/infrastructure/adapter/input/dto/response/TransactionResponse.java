package africa.semicolon.ppay.infrastructure.adapter.input.dto.response;


import africa.semicolon.ppay.domain.model.DateCreated;
import africa.semicolon.ppay.domain.model.TransactionStatus;
import africa.semicolon.ppay.domain.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private Long id;
    private TransactionStatus status;
    private TransactionType transactionType;
    private BigDecimal amount;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;
}
