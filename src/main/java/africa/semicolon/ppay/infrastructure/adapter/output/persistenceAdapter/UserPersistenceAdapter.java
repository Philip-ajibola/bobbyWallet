package africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter;

import africa.semicolon.ppay.application.ports.output.UserOutputPort;
import africa.semicolon.ppay.domain.exception.UserNotFoundException;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.infrastructure.adapter.output.mappers.EntityMappers;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.UserEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPersistenceAdapter implements UserOutputPort {
    private final UserEntityRepo userEntityRepo;
    public UserPersistenceAdapter(UserEntityRepo userEntity) {
        this.userEntityRepo = userEntity;
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = EntityMappers.INSTANCE.toEntity(user);
        userEntity = userEntityRepo.save(userEntity);
        return EntityMappers.INSTANCE.toModel(userEntity);
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userEntityRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException(String.format("User with id %s not found",id)));
        System.out.println(userEntity.getDateCreated());
        return EntityMappers.INSTANCE.toModel(userEntity);
    }

    @Override
    public void delete(Long id) {
            UserEntity userEntity = userEntityRepo.findById(id)
                    .orElseThrow(()->new UserNotFoundException(String.format("User with id %s not found",id)));
            userEntityRepo.delete(userEntity);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = EntityMappers.INSTANCE.toEntity(user);
        userEntityRepo.delete(userEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return userEntityRepo.existsById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        UserEntity userEntity = userEntityRepo.findUserEntityByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
        User user = EntityMappers.INSTANCE.toModel(userEntity);
        return user;
    }
}
