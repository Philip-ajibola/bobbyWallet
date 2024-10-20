package africa.semicolon.bobbywallet.domain.service;

import africa.semicolon.bobbywallet.adapter.output.repository.UserRepo;
import africa.semicolon.bobbywallet.application.dto.request.*;
import africa.semicolon.bobbywallet.application.dto.response.*;
import africa.semicolon.bobbywallet.application.ports.input.PaymentService;
import africa.semicolon.bobbywallet.application.ports.input.TransactionService;
import africa.semicolon.bobbywallet.application.ports.input.UserService;
import africa.semicolon.bobbywallet.application.ports.input.WalletService;
import africa.semicolon.bobbywallet.domain.exception.BobbyWalletException;
import africa.semicolon.bobbywallet.domain.exception.UserNotFoundException;
import africa.semicolon.bobbywallet.domain.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final ModelMapper mapper;
    private final WalletService walletService;
    private  ObjectMapper objectMapper;
    private final TransactionService transactionService;
    @Autowired
    public UserServiceImpl(UserRepo userRepository, ModelMapper mapper, WalletService walletService, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = getUser(userId);
        return mapper.map(user,UserResponse.class);
    }
    @Override
    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(String.format("User With  id %s not found",userId)));
        return user;
    }

    @Override
    public UserResponse createUser(CreateUserDto userDto) {
        User user = mapper.map(userDto,User.class);
        user = userRepository.save(user);
        user.setWallet(walletService.createWallet(user.getId()));
        userRepository.save(user);
        return mapper.map(user,UserResponse.class);
    }

    @Override
    public UserResponse updateUser(Long userId, JsonPatch request) {
        try {
            User user = getUser(userId);
            objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.convertValue(user, JsonNode.class);

            jsonNode = request.apply(jsonNode);
            user = objectMapper.convertValue(jsonNode, User.class);
            user = userRepository.save(user);
            return mapper.map(user, UserResponse.class);
        }catch (JsonPatchException e){
            log.error(e.getMessage());
            throw new BobbyWalletException(e.getMessage());
        }
    }

    @Override
    public String deleteUser(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
        return "Deleted Successfully";
    }

    @Override
    public InitializePaymentResponse deposit(InitializePaymentDto request, @Autowired PaymentService paymentService) {
        return walletService.deposit(request, paymentService);
    }


    @Override
    public InitializeTransferResponse transfer(TransferDto request, @Autowired PaymentService paymentService) {
        return walletService.transfer(request,paymentService);
    }

    @Override
    public List<TransactionResponse> allTransaction(Long userId) {
        return transactionService.viewAllTransaction(userId);
    }

    @Override
    public TransactionResponse viewTransaction(Long transactionId) {
        return transactionService.viewTransaction(transactionId);
    }
}
