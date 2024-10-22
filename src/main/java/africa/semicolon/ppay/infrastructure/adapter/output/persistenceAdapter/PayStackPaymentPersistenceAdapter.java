package africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter;

import africa.semicolon.ppay.application.ports.output.PayStackPaymentOutputPort;
import africa.semicolon.ppay.domain.exception.PaymentNotFoundException;
import africa.semicolon.ppay.domain.model.Payment;
import africa.semicolon.ppay.infrastructure.adapter.output.mappers.EntityMappers;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.PaymentEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.PayStackPaymentEntityRepo;

public class PayStackPaymentPersistenceAdapter implements PayStackPaymentOutputPort {
    private final PayStackPaymentEntityRepo payStackPaymentEntityRepo;

    public PayStackPaymentPersistenceAdapter(PayStackPaymentEntityRepo payStackPaymentEntityRepo) {
        this.payStackPaymentEntityRepo = payStackPaymentEntityRepo;
    }

    @Override
    public Payment savePayment(Payment payment) {
        PaymentEntity entity = EntityMappers.INSTANCE.toEntity(payment);
        entity = payStackPaymentEntityRepo.save(entity);
        return EntityMappers.INSTANCE.toModel(entity);
    }

    @Override
    public Payment findPaymentById(Long id) {
        PaymentEntity entity = payStackPaymentEntityRepo.findById(id).orElseThrow(()->new PaymentNotFoundException("Payment With "+ id + " Not Found"));
        return EntityMappers.INSTANCE.toModel(entity);
    }
}
