package africa.semicolon.ppay.domain.service;

import africa.semicolon.ppay.application.ports.input.userUseCase.*;
import africa.semicolon.ppay.application.ports.output.UserOutputPort;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.model.Wallet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements CreateUserUseCase, FindByIdUseCase, UpdateUserUseCase, DeleteUserUseCase, ExistByIdUseCase,ChangePinUseCase {
    private final UserOutputPort userOutputPort;
    private final ObjectMapper objectMapper = new ObjectMapper();;

    public UserService( UserOutputPort userOutputPort) {
        this.userOutputPort = userOutputPort;
    }

    @Override
    public void delete(Long userId) {
        userOutputPort.delete(userId);
    }

    @Override
    public void delete(User user) {
        userOutputPort.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userOutputPort.getUserById(id);
    }

    @Override
    public User createUser(User user,WalletService walletService) {
        User user1 = userOutputPort.saveUser(user);
        Wallet wallet = walletService.createWallet(user1.getId());
        user1.setWallet(wallet);
        return userOutputPort.saveUser(user);
    }

    @Override
    public User updateUser(Long id, JsonPatch request) throws JsonPatchException {
        User user = findById(id);
        JsonNode jsonNode = objectMapper.convertValue(user, JsonNode.class);
        jsonNode = request.apply(jsonNode);
        user = objectMapper.convertValue(jsonNode, User.class);
        user = userOutputPort.saveUser(user);
        return user;
    }

    @Override
    public boolean existById(Long id) {
        return userOutputPort.existsById(id);
    }

    @Override
    public User changePin(Long userId, String password, String newPin,WalletService walletService) {
        User user = userOutputPort.getUserById(userId);
        if(!user.getPassword().equals(password))throw new PPayWalletException("Invalid Credentials");
       Wallet wallet =  walletService.updatePin(userId,newPin);
       user.setWallet(wallet);
       return userOutputPort.saveUser(user);
    }
}
