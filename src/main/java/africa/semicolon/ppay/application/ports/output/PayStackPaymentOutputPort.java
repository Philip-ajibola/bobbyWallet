package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.Payment;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.CreateTransferRecipientDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializePaymentDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;

public interface PayStackPaymentOutputPort {
    Payment savePayment(Payment payment);
    Payment findPaymentById(Long id);
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference, Long id);
    CreateTransferRecipientResponse createTransferRecipient(CreateTransferRecipientDto createTransferRecipientDto);
    InitializeTransferResponse transfer(InitializeTransferDto request);
    TransferVerificationResponse transferVerification(String reference, Long id);
}
