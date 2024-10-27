package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.application.ports.input.walletUseCase.*;
import africa.semicolon.ppay.application.ports.output.WalletOutputPort;
import africa.semicolon.ppay.domain.exception.InvalidAmountException;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.TransactionStatus;
import africa.semicolon.ppay.domain.model.TransactionType;
import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.*;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

public class WalletService implements CreateWalletUseCase, DepositUseCase, FindByIdUseCase,
        TransferUseCase, ExistByIdUseCase,ChangePinUseCase,VerifyTransferUseCase,VerifyPaymentUseCase {

    private  PayStackPaymentService payStackPaymentService;

    private TransactionService transactionService;
    private final WalletOutputPort walletOutputPort;

    public WalletService(WalletOutputPort walletOutputPort,TransactionService transactionService,PayStackPaymentService payStackPaymentService) {
        this.walletOutputPort = walletOutputPort;
        this.transactionService = transactionService;
        this.payStackPaymentService=payStackPaymentService;
    }

    @Override
    public Wallet findById(Long id) {
        return walletOutputPort.getWalletById(id);
    }

    @Override
    public Wallet createWallet(Long walletId) {
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        return walletOutputPort.saveWallet(wallet);
    }

    @Override
    public boolean existById(Long id) {
        return walletOutputPort.existById(id);
    }

    @Override
    @Transactional
    public InitializePaymentResponse deposit(DepositDto depositDto) {
        Wallet wallet = findById(depositDto.getId());
        if(depositDto.getAmount().compareTo(BigDecimal.ZERO)<0) throw new InvalidAmountException("Enter A Valid Input");
        InitializePaymentResponse initializePaymentResponse = createPayment(depositDto);
        return initializePaymentResponse;
    }

    private InitializePaymentResponse createPayment(DepositDto depositDto) {
        InitializePaymentDto initializePaymentDto = InitializePaymentDto.builder()
                .email(depositDto.getEmail())
                .amount(depositDto.getAmount())
                .id(depositDto.getId())
                .build();
        InitializePaymentResponse initializePaymentResponse = payStackPaymentService.initializePayment(initializePaymentDto);
        return initializePaymentResponse;
    }

    private void createDepositTransaction(PaymentVerificationResponse paymentVerificationResponse, Wallet wallet) {
        Transaction transaction = new Transaction();
        transaction.setAmount(paymentVerificationResponse.getData().getAmount());
        transaction.setWallet(findById(wallet.getId()));
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setStatus(paymentVerificationResponse.isStatus()? TransactionStatus.SUCCESSFUL : TransactionStatus.FAILED);
        transactionService.save(transaction);
    }
    private void createTransferTransaction(TransferVerificationResponse paymentVerificationResponse,Long id) {
            Wallet wallet = findById(id);
            Transaction transaction = new Transaction();
            transaction.setAmount(paymentVerificationResponse.getData().getAmount());
            transaction.setWallet(findById(wallet.getId()));
            transaction.setTransactionType(TransactionType.DEPOSIT);
            transaction.setStatus(paymentVerificationResponse.isStatus()? TransactionStatus.SUCCESSFUL : TransactionStatus.FAILED);
            transactionService.save(transaction);
        }

    @Override
    public InitializeTransferResponse transfer(TransferDto dto) {
        Wallet wallet = findById(dto.getId());
        if(dto.getAmount().compareTo(BigDecimal.ZERO)<0)throw new InvalidAmountException("Enter A Valid Amount");
        if (!wallet.getPin().equals(dto.getPin())) throw new PPayWalletException("You Provided Wrong PIN");
        CreateTransferRecipientResponse createTransferRecipientResponse = payStackPaymentService.createTransferRecipient(new CreateTransferRecipientDto(dto.getId(), dto.getAccountNumber(), dto.getName(),dto.getBankCode()));
        InitializeTransferResponse initializeTransferResponse = payStackPaymentService.transfer(new InitializeTransferDto(dto.getId(), dto.getAmount(), createTransferRecipientResponse.getData().getRecipientCode()));
        return initializeTransferResponse;
    }

    @Override
    public Wallet updatePin(Long id, String pin) {
        if(pin.isBlank() && pin.length() != 4 && !pin.matches("\\d+")) throw new PPayWalletException("Pin Must Not Be Empty Or Alphabetical Or less than or greater than 4 digits ");
        Wallet wallet = findById(id);
        wallet.setPin(pin);
        return walletOutputPort.saveWallet(wallet);
    }


    @Override
    public Wallet verifyTransfer(VerifyPaymentDto dto) {
        TransferVerificationResponse response = payStackPaymentService.transferVerification(dto.getReference(), dto.getId());
        Wallet wallet = findById(dto.getId());
        wallet.transfer(dto.getAmount());
        createTransferTransaction(response,dto.getId());
        return walletOutputPort.saveWallet(wallet);
    }

    @Override
    public Wallet verifyPayment(VerifyPaymentDto verifyPaymentDto) {
        PaymentVerificationResponse paymentVerificationResponse = payStackPaymentService.paymentVerification(verifyPaymentDto.getReference(), verifyPaymentDto.getId());
        Wallet wallet = findById(verifyPaymentDto.getId());
        wallet.deposit(verifyPaymentDto.getAmount());
        wallet = walletOutputPort.saveWallet(wallet);
        createDepositTransaction(paymentVerificationResponse,wallet);
        return wallet;
    }
}
