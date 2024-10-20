package africa.semicolon.bobbywallet.adapter.output.repository;

import africa.semicolon.bobbywallet.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,Long> {
}
