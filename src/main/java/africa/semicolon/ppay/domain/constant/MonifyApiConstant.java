package africa.semicolon.ppay.domain.constant;

public class MonifyApiConstant {
    private static String base_url="https://sandbox.monnify.com";
    public  static String  LOGIN_URL=base_url+"/api/v1/auth/login";
//    public  static String  TRANSFER_URL=base_url+"/api/v2/transfer";
    public  static String  INITIATE_TRANSACTION=base_url+"/api/v1/merchant/transactions/init-transaction";
    public static String VERIFY_TRANSACTION=base_url + "/api/v2/transactions/";
    public static String INITIATE_TRANSFER=base_url + "/api/v2/disbursements/single";
    public static String AUTHORIZE_TRANSFER=base_url + "/api/v2/disbursements/single/validate-otp";
    public static String VERIFY_TRANSFER=base_url + "/api/v2/disbursements/single/summary?reference=";
}
