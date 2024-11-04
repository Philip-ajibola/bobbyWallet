package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.model.User;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

public interface UpdateUserUseCase {
    User updateUser(Long id, JsonPatch request) ;
}
