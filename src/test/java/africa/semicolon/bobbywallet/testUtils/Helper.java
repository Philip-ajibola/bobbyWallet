package africa.semicolon.bobbywallet.testUtils;

import africa.semicolon.bobbywallet.application.dto.request.InitializePaymentDto;
import africa.semicolon.bobbywallet.application.dto.request.TransferDto;
import africa.semicolon.bobbywallet.application.dto.response.InitializePaymentResponse;
import africa.semicolon.bobbywallet.application.ports.input.PaymentService;
import africa.semicolon.bobbywallet.application.ports.input.UserService;
import africa.semicolon.bobbywallet.application.ports.input.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;

@Component
public class Helper {
    @Autowired
    private static PaymentService paymentService;
    public static TransferDto initiateTransfer(Long id, Long transferId) {
        initiateDeposit(id);
        TransferDto transferDto = new TransferDto();
        transferDto.setAmount(BigDecimal.valueOf(500));
        transferDto.setId(transferId);
        transferDto.setName("John Doe");
        transferDto.setBankCode("999992");
        transferDto.setAccountNumber("9027531222");
        return transferDto;
    }

    private static InitializePaymentDto initiateDeposit(Long id){
        InitializePaymentDto initializePaymentDto = new InitializePaymentDto();
        initializePaymentDto.setId(id);
        initializePaymentDto.setAmount(BigDecimal.valueOf(1000));
        initializePaymentDto.setEmail("johndoe@example.com");
        return initializePaymentDto;
    }
    public static InitializePaymentResponse initiateDeposit(Long id,@Autowired WalletService walletService) {
        InitializePaymentDto initializePaymentDto =initiateDeposit(id);
        InitializePaymentResponse response = walletService.deposit(initializePaymentDto,paymentService);
        return response;
    }
    public static InitializePaymentResponse initiateDeposit(Long id,@Autowired UserService userService) {
        InitializePaymentDto initializePaymentDto =initiateDeposit(id);
        InitializePaymentResponse response = userService.deposit(initializePaymentDto,paymentService);
        return response;
    }
}
