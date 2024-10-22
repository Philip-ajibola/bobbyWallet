package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.Payment;

public interface PayStackPaymentOutputPort {
    Payment savePayment(Payment payment);
    Payment findPaymentById(Long id);
}
