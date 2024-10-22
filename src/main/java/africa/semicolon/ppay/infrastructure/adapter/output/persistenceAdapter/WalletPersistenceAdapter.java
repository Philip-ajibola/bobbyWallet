package africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter;

import africa.semicolon.ppay.application.ports.output.WalletOutputPort;
import africa.semicolon.ppay.domain.exception.WalletNotFoundException;
import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.output.mappers.EntityMappers;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.WalletEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.WalletEntityRepo;

public class WalletPersistenceAdapter implements WalletOutputPort {
    private final WalletEntityRepo walletEntityRepo;

    public WalletPersistenceAdapter(WalletEntityRepo walletEntityRepo) {
        this.walletEntityRepo = walletEntityRepo;
    }

    @Override
    public Wallet saveWallet(Wallet wallet) {
        WalletEntity walletEntity = EntityMappers.INSTANCE.toEntity(wallet);
        return EntityMappers.INSTANCE.toModel(walletEntity);
    }

    @Override
    public Wallet getWalletById(Long id) {
        WalletEntity walletEntity = walletEntityRepo.findById(id)
                .orElseThrow(()->new WalletNotFoundException(String.format("Wallet with id %d not found",id)));
        return EntityMappers.INSTANCE.toModel(walletEntity);
    }

    @Override
    public boolean existById(Long id) {
        return walletEntityRepo.existsById(id);
    }
}
