package africa.semicolon.bobbywallet.domain.service;

import africa.semicolon.bobbywallet.adapter.output.repository.WalletRepo;
import africa.semicolon.bobbywallet.application.dto.request.*;
import africa.semicolon.bobbywallet.application.dto.response.InitializePaymentResponse;
import africa.semicolon.bobbywallet.application.dto.response.InitializeTransferResponse;
import africa.semicolon.bobbywallet.application.dto.response.WalletResponse;
import africa.semicolon.bobbywallet.application.ports.input.PaymentService;
import africa.semicolon.bobbywallet.application.ports.input.WalletService;
import africa.semicolon.bobbywallet.domain.exception.InsufficientFundsException;
import africa.semicolon.bobbywallet.domain.exception.InvalidAmountException;
import africa.semicolon.bobbywallet.domain.exception.WalletNotFoundException;
import africa.semicolon.bobbywallet.domain.model.Wallet;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepo repository;
    private final ModelMapper mapper;
    @Autowired
    public WalletServiceImpl(WalletRepo repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Wallet createWallet(Long userId) {
        Wallet wallet = new Wallet(userId);
        return repository.save(wallet);
    }

    @Override
    @Transactional
    public InitializePaymentResponse deposit(InitializePaymentDto request, @Autowired  PaymentService paymentService) {
        getWallet(request.getId());
        if(request.getAmount().compareTo(BigDecimal.ZERO)<0)throw new InvalidAmountException("You Have Entered Invalid Amount");
        var response = paymentService.initializePayment(request);
        return response;
    }

    @Override
    @Transactional
    public InitializeTransferResponse transfer(TransferDto request, @Autowired  PaymentService paymentService) {
        Wallet senderWallet = getWallet(request.getId());
        if(request.getAmount().compareTo(senderWallet.getBalance())>0)throw new InsufficientFundsException("Insufficient funds");
        var response = paymentService.createTransferRecipient(new CreateTransferRecipientDto(request.getAccountNumber(),request.getName(),request.getBankCode()));
        var transferResponse = paymentService.transfer( new InitializeTransferDto(request.getId(),request.getAmount(),response.getData().getRecipientCode()));
        return transferResponse;
    }

    private Wallet getWallet(Long id) {
        Wallet senderWallet = repository.findById(id).orElseThrow(()->new WalletNotFoundException(String.format("Wallet with id %s not found",id)));
        return senderWallet;
    }

    @Override
    public WalletResponse getBalance(Long id) {
        Wallet wallet = getWallet(id);
        return mapper.map(wallet, WalletResponse.class);
    }

    @Override
    public Wallet save(Wallet wallet) {
        if(wallet == null) throw new WalletNotFoundException("Wallet is NULL");
        return repository.save(wallet);
    }

    @Override
    public Wallet getById(Long id) {
        return repository.findById(id).orElseThrow(()->new WalletNotFoundException(String.format("Wallet with id %s not found",id)));
    }
}
