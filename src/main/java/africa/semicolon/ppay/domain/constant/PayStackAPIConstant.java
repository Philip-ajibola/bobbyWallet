package africa.semicolon.ppay.domain.constant;

public class PayStackAPIConstant {
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_CREATED = 201;
    public static final String PAYSTACK_FINALIZE_TRANSFER="https://api.paystack.co/transfer/finalize_transfer";
    public static final String PAYSTACK_CREATE_TRANSFER_RECIPIENT = "https://api.paystack.co/transferrecipient";
    public static final String PAYSTACK_INITIALIZE_TRANSFER= "https://api.paystack.co/transfer";
    public static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
    public static final String PAYSTACK_VERIFY_TRANSFER = "https://api.paystack.co/transfer/verify/";
    public static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
    public static final String PREMBLY_VERIFY_WITH_NIN="https://api.prembly.com/identitypass/verification/nin_w_face";
}
