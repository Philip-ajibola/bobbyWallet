package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.request.*;
import africa.semicolon.bobbywallet.application.dto.response.*;
import africa.semicolon.bobbywallet.domain.model.User;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long userId);
    UserResponse createUser(CreateUserDto userDto);
    UserResponse updateUser(Long userId, JsonPatch request);
    String deleteUser(Long userId);
    InitializePaymentResponse deposit(InitializePaymentDto request, PaymentService paymentService);
    InitializeTransferResponse transfer(TransferDto request, PaymentService paymentService);
    List<TransactionResponse> viewAllTransactions(Long userId);
    TransactionResponse viewTransaction(Long transactionId);
    User getUser(Long id);
}
