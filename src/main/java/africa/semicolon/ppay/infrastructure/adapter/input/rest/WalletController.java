package africa.semicolon.ppay.infrastructure.adapter.input.rest;

import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.domain.service.WalletService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.DepositDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.TransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyPaymentDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.ApiResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializePaymentResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializeTransferResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.WalletResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.mappers.DtoMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;
    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("/get_by/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        Wallet wallet = walletService.findById(id);
        WalletResponse  response= DtoMappers.INSTANCE.toWalletResponse(wallet);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("SuccessFul", response,true));
    }
    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> deposit(@RequestBody DepositDto  depositDto){
        InitializePaymentResponse response = walletService.deposit(depositDto);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("SuccessFul", response,true));
    }
    @PostMapping("/verify/deposit")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> verifyPayment(@RequestBody VerifyPaymentDto verifyPaymentDto){
        Wallet wallet = walletService.verifyPayment(verifyPaymentDto);
        WalletResponse  response= DtoMappers.INSTANCE.toWalletResponse(wallet);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("SuccessFul", response,true));
    }
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto){
        InitializeTransferResponse response = walletService.transfer(transferDto);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("SuccessFul", response,true));
    }

    @PostMapping("/verify_transfer")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> verifyTransfer(@RequestBody VerifyPaymentDto verifyPaymentDto){
        Wallet wallet = walletService.verifyTransfer(verifyPaymentDto);
        WalletResponse  response= DtoMappers.INSTANCE.toWalletResponse(wallet);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("SuccessFul", response,true));
    }
    @GetMapping("/pay_completed")
    public String payCompleted(){
        return "<h1>Payment Completed</h1>";
    }

}
