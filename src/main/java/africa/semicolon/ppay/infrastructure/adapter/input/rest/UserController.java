package africa.semicolon.ppay.infrastructure.adapter.input.rest;

import africa.semicolon.ppay.application.ports.input.transactionUseCase.GetAllTransactionUseCase;
import africa.semicolon.ppay.application.ports.input.transactionUseCase.ViewAllTransactionUseCase;
import africa.semicolon.ppay.application.ports.input.userUseCase.*;
import africa.semicolon.ppay.application.ports.input.walletUseCase.ChangeWalletPinUseCase;
import africa.semicolon.ppay.application.ports.input.walletUseCase.CreateWalletUseCase;
import africa.semicolon.ppay.domain.service.TransactionService;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.User;
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
    private final CreateUserUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final ChangePinUseCase changePinUseCase;
    private final africa.semicolon.ppay.application.ports.input.userUseCase.GetAllTransactionUseCase getAllTransactionUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final FindByEmailUseCase findByEmailUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final GetAllUserUseCase getAllUsers;
    private final ChangeWalletPinUseCase changeWalletPinUseCase;
    private final CreateWalletUseCase createWalletUseCase;
    private final ViewAllTransactionUseCase userTransactionUseCase;

    @Autowired
    public UserController(CreateUserUseCase registerUseCase, LoginUseCase loginUseCase, ChangePinUseCase changePinUseCase, africa.semicolon.ppay.application.ports.input.userUseCase.GetAllTransactionUseCase getAllTransactionUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, ResetPasswordUseCase resetPasswordUseCase, FindByEmailUseCase findByEmailUseCase, FindByIdUseCase findByIdUseCase, GetAllUserUseCase getAllUsers, ChangeWalletPinUseCase changeWalletPinUseCase, WalletService walletService, CreateWalletUseCase createWalletUseCase, GetAllTransactionUseCase userTransactionUseCase, TransactionService transactionService, ViewAllTransactionUseCase userTransactionUseCase1) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.changePinUseCase = changePinUseCase;
        this.getAllTransactionUseCase = getAllTransactionUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.findByEmailUseCase = findByEmailUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.getAllUsers = getAllUsers;
        this.changeWalletPinUseCase = changeWalletPinUseCase;
        this.createWalletUseCase = createWalletUseCase;
        this.userTransactionUseCase = userTransactionUseCase1;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody  CreateUserDto userDto){
        User user = DtoMappers.INSTANCE.toUser(userDto);
        user = registerUseCase.createUser(user,createWalletUseCase);
        WalletResponse wallet = DtoMappers.INSTANCE.toWalletResponse(user.getWallet());
        var finalResponse = DtoMappers.INSTANCE.toUserResponse(user);
        finalResponse.setWallet(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Registration Successfully", finalResponse,true));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = loginUseCase.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Login Successfully", response,true));
    }
    @GetMapping("/find_by_id/{userId}")
    @PreAuthorize("hasAnyRole('USERS','ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId){
        User user = findByIdUseCase.findById(userId);
        UserResponse response =  DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successful", response,true));

    }
    @GetMapping("/find_by_email/{email}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email){
        User user = findByEmailUseCase.findByEmail(email);
        UserResponse response =  DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successful", response,true));
    }
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> updateUser(@RequestBody JsonPatch request, @PathVariable("id") Long userId)  {
        User user = updateUserUseCase.updateUser(userId, request);
        UserResponse response = DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User updated successfully", response, true));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USERS','ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId){
        deleteUserUseCase.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User deleted successfully", "Deleted Successful", true));
    }
    @PatchMapping("/change_pin")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> changePin(@RequestBody @Valid  ChangePinRequest request){
        User user = changePinUseCase.changePin(request.getId(), request.getPassword(), request.getNewPin(), changeWalletPinUseCase);
        UserResponse response = DtoMappers.INSTANCE.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Pin changed successfully", response, true));
    }
    @PatchMapping("/reset_password")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        var response = resetPasswordUseCase.resetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Password reset successfully", response, true));
    }
    @PatchMapping("/get_all_users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(){
        List<User> response = getAllUsers.getAllUsers();
        List<UserResponse> responseList = response.stream().map(user -> DtoMappers.INSTANCE.toUserResponse(user)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("List Of Users", responseList, true));
    }
    @GetMapping("/getALlTransactions/{id}")
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<?> getAllTransactions(@PathVariable("id") Long userId){
        List<Transaction> transactions = getAllTransactionUseCase.getAllTransactions(userId,userTransactionUseCase);
        List<TransactionResponse> response = transactions.stream().map(transaction -> DtoMappers.INSTANCE.toTransactionResponse(transaction)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("All Transactions", response,true));
    }
}
