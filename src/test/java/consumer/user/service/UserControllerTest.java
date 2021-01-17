package consumer.user.service;

import org.example.consumer.user.service.controller.UserClient;
import org.example.consumer.user.service.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserController.class})
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserClient userClient;

    @Test
    public void testGetAllUsers() {
        assertNotNull(userController);
    }

}
