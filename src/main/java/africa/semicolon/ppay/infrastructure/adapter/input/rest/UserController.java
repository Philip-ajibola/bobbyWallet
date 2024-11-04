package africa.semicolon.ppay.infrastructure.adapter.input.rest;

import africa.semicolon.ppay.domain.service.TransactionService;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.service.UserService;
import africa.semicolon.ppay.domain.service.WalletService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.ChangePinRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.CreateUserDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.LoginRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;
import africa.semicolon.ppay.infrastructure.adapter.input.mappers.DtoMappers;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    @Autowired
    public UserController(UserService userService, WalletService walletService, TransactionService transactionService) {
        this.userService = userService;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody  CreateUserDto userDto){
        User user = DtoMappers.INSTANCE.toUser(userDto);
        user = userService.createUser(user,walletService);
        WalletResponse wallet = DtoMappers.INSTANCE.toWalletResponse(user.getWallet());
        var finalResponse = DtoMappers.INSTANCE.toUserResponse(user);
        finalResponse.setWallet(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Registration Successfully", finalResponse,true));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Login Successfully", response,true));
    }
    @GetMapping("/find_by_id/{userId}")
    @PreAuthorize("hasAnyRole('USERS','ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId){
        User user = userService.findById(userId);
        UserResponse response =  DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successful", response,true));

    }
    @GetMapping("/find_by_email/{email}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email){
        User user = userService.findByEmail(email);
        UserResponse response =  DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successful", response,true));
    }
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> updateUser(@RequestBody JsonPatch request, @PathVariable("id") Long userId){
        User user = userService.updateUser(userId, request);
        UserResponse response = DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User updated successfully", response, true));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USERS','ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId){
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User deleted successfully", "Deleted Successful", true));
    }
    @PatchMapping("/change_pin")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> changePin(@RequestBody @Valid  ChangePinRequest request){
        User user = userService.changePin(request.getId(), request.getPassword(), request.getNewPin(), walletService);
        UserResponse response = DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Pin changed successfully", response, true));
    }
    @PatchMapping("/reset_password")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        var response = userService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Password reset successfully", response, true));
    }
    @PatchMapping("/get_all_users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(){
        List<User> response = userService.getAllUsers();
        List<UserResponse> responseList = response.stream().map(user -> DtoMappers.INSTANCE.toUserResponse(user)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("List Of Users", responseList, true));
    }
    @GetMapping("/getALlTransactions/{id}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> getAllTransactions(@PathVariable("id") Long userId){
        List<Transaction> transactions = userService.getAllTransactions(userId,transactionService);
        List<TransactionResponse> response = transactions.stream().map(transaction -> DtoMappers.INSTANCE.toTransactionResponse(transaction)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("All Transactions", response,true));
    }
}
