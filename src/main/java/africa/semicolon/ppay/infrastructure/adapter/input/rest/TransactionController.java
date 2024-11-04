package africa.semicolon.ppay.infrastructure.adapter.input.rest;

import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.service.TransactionService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.ApiResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.TransactionResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.mappers.DtoMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping("/getTransaction/{id}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> getTransaction(@PathVariable("id") Long id){
        Transaction transaction = transactionService.viewTransaction(id);
        TransactionResponse transactionResponse= DtoMappers.INSTANCE.toTransactionResponse(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Transaction", transactionResponse,true));
    }

    @GetMapping("/getALlTransactions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTransactions(){
        List<Transaction> transactions = transactionService.getAllTransaction();
        List<TransactionResponse> response = transactions.stream().map(transaction -> DtoMappers.INSTANCE.toTransactionResponse(transaction)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("All Transactions", response,true));
    }
}
