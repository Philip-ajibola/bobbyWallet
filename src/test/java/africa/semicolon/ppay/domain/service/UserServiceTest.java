package africa.semicolon.ppay.domain.service;

import africa.semicolon.ppay.domain.exception.UserNotFoundException;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.service.UserService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.LoginRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.ResetPasswordRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.LoginResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.UserResponse;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@Slf4j
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testThatUserCanBeDeleted() {
        userService.delete(103L);
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
    public void testThatUserCanLogin(){
        LoginRequest request = new LoginRequest();
        request.setEmail("ajibola@gmail.com");
        request.setPassword("password");
        LoginResponse response = userService.login(request);
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        log.info("User {}",response);
    }

    @Test
    void testThatUserCanBeUpdated() throws JsonPointerException, JsonPatchException {
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/firstname"),new TextNode("James"))
        );
        JsonPatch request = new JsonPatch(operations);
        User user =  userService.updateUser(103L,request);
        assertThat(user.getFirstname()).isEqualTo("James");
    }
    @Test
    void testThatUserCanBeFoundUsingEmail(){
        User user = userService.findByEmail("johndoe@example.com");
        assertNotNull(user);
        assertThat(user.getFirstname()).isEqualTo("John");
    }
    @Test
    void testThatUserCanResetPassword(){
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setId(103L);
        request.setNewPassword("newPassword");
        request.setOldPassword("password");
        UserResponse user = userService.resetPassword(request);
        assertNotNull(user);
        assertThat(user.getFirstname()).isEqualTo("philip");
    }
    @Test
    void testThatAdminCanGetAllUsers(){
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertThat(users.size()).isEqualTo(3);
    }
}