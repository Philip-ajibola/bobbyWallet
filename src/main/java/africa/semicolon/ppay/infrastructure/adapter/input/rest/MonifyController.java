package africa.semicolon.ppay.infrastructure.adapter.input.rest;

import africa.semicolon.ppay.application.ports.input.monifyUseCase.*;
import africa.semicolon.ppay.domain.service.MonifyUserService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.MonifyInitializePaymentRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.AuthorizeRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.MonifyInitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/monify")
public class MonifyController {

    private final InitializePaymentUseCase initializePaymentUseCase;
    private final VerifyPaymentUseCase verifyPaymentUseCase;
    private final VerifyTransferUseCase verifyTransferUseCase;
    private final TransferUseCase transferUseCase;
    private final AuthorizeTransferUseCase authorizeTransferUseCase;

    public MonifyController( InitializePaymentUseCase initializePaymentUseCase, VerifyPaymentUseCase verifyPaymentUseCase, VerifyTransferUseCase verifyTransferUseCase, TransferUseCase transferUseCase, AuthorizeTransferUseCase authorizeTransferUseCase) {
        this.initializePaymentUseCase = initializePaymentUseCase;
        this.verifyPaymentUseCase = verifyPaymentUseCase;
        this.verifyTransferUseCase = verifyTransferUseCase;
        this.transferUseCase = transferUseCase;
        this.authorizeTransferUseCase = authorizeTransferUseCase;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid MonifyInitializePaymentRequest request) {
        var response = initializePaymentUseCase.initializePayment(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Deposit SuccessFully Initiated ", response,true));

    }
    @GetMapping("/verify/deposit/{reference}")
    public ResponseEntity<?> verifyTransaction(@PathVariable("reference") String  reference) {
        var response = verifyPaymentUseCase.verifyTransaction(reference);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Transaction Status", response,true));

    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid  MonifyInitializeTransferDto request) {
        var response = transferUseCase.initializeTransfer(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Transfer Successfully Initiated ", response,true));

    }
    @PostMapping("/authorize")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> authorizeTransfer(@Valid AuthorizeRequest request) {
        var response = authorizeTransferUseCase.authorizeTransfer(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Transaction Authorized ", response,true));

    }
    @GetMapping("/verify/transfer")
    public ResponseEntity<?> verifyTransfer(@RequestParam String reference) {
        var response = verifyTransferUseCase.verifyTransfer(reference);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Transaction Verified ", response,true));

    }
}
