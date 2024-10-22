package africa.semicolon.ppay.domain.service;

import africa.semicolon.ppay.domain.exception.UserNotFoundException;
import africa.semicolon.ppay.domain.model.User;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testThatUserCanBeDeleted() {
        userService.delete(101L);
        assertThrows(UserNotFoundException.class,()->userService.findById(101L));
    }

    @Test
    void testThatUserCanUserCanBeFoundWithUseId() {
        User user = userService.findById(101L);
        assertNotNull(user);
        assertThat(user.getFirstname()).isEqualTo("John");
    }

    @Test
    void testThatUserCantBeFoundWithInvalidId() {
        assertThrows(UserNotFoundException.class,()->userService.findById(107L));
    }

    @Test
    void testThatUserCanBeUpdated() throws JsonPointerException, JsonPatchException {
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/firstname"),new TextNode("James"))
        );
        JsonPatch request = new JsonPatch(operations);
        User user =  userService.updateUser(101L,request);
        assertThat(user.getFirstname()).isEqualTo("James");

    }
}