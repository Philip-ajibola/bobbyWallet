package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.domain.exception.WalletNotFoundException;
import africa.semicolon.ppay.domain.model.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class WalletServiceTest {
    @Autowired
    private WalletService walletService;
    @Test
    void testThatWalletCanBeFoundUsingId() {
        Wallet wallet = walletService.findById(101L);
        assertNotNull(wallet);
        assertThat(wallet.getBalance()).isEqualTo(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
    }
    @Test
    void testThatFindingThatWithInvalidIdThrowsException() {
        assertThrows(WalletNotFoundException.class,()->walletService.findById(103L));
    }

}