package consumer.user.service;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.example.consumer.user.service.controller.UserClient;
import org.example.consumer.user.service.controller.UserController;
import org.example.consumer.user.service.schema.SkillDTO;
import org.example.consumer.user.service.schema.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserController.class, UserClient.class})
@AutoConfigureWebClient
@PactTestFor(port = "8083")
//@TestPropertySource(properties = {
// enable if test fails to get more details in log
//        "logging.level.au.com.dius.pact=DEBUG"
//})
@ExtendWith(PactConsumerTestExt.class)
public class UserControllerConsumerPactTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserClient userClient;

    private UserDTO createUserDTO() {
        SkillDTO skillDTO1 = new SkillDTO();
        skillDTO1.setSkillId("456");
        skillDTO1.setSkillName("Creating Pact Consumer Tests");

        UserDTO exampleUserDTO = new UserDTO();
        exampleUserDTO.setUserId("123");
        exampleUserDTO.setUserName("Pactman");
        exampleUserDTO.setSkillDtos(Collections.singletonList(skillDTO1));

        return exampleUserDTO;
    }

    @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
    public RequestResponsePact createPact_GetAllUsers(PactDslWithProvider builder) {
        return builder
                .uponReceiving("getAllUsers test interaction response")
                .path("/user/getAllUsers")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(
                        // https://docs.pact.io/implementation_guides/jvm/consumer
                        PactDslJsonArray.arrayEachLike()
                                .integerType("userId", 123)
                                .stringType("userName", "Pactman")
                                .eachLike("skillDtos")
                                .integerType("skillId", 456)
                                .stringType("skillName", "Creating Pact Consumer Tests")
                                .closeArray()
                                .closeObject()
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact_GetAllUsers")
    public void testCreatePact_GetAllUsers() {
        List<UserDTO> responseOfMockServer = assertDoesNotThrow(() -> userController.getAllUsers());
        assertFalse(responseOfMockServer.isEmpty(), "Response must not be empty, because we defined the body, having an array with one example dto.");
        assertEquals(Collections.singletonList(createUserDTO()), responseOfMockServer, "Response body contained a different user dto as expected.");
    }

    private static final String USER_ID_PARAM_NAME = "userId";
    public static final int USER_ID_EXAMPLE_VALUE = 101;

    @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
    public RequestResponsePact createPact_GetAllUsersWithProviderState(PactDslWithProvider builder) {

        return builder
                .given("User with user id ${" + USER_ID_PARAM_NAME + "} exists", USER_ID_PARAM_NAME, USER_ID_EXAMPLE_VALUE)
                .uponReceiving("getAllUsers test interaction response for user with " + USER_ID_PARAM_NAME + " = " + USER_ID_EXAMPLE_VALUE)
                .path("/user/getAllUsers")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(
                        // https://docs.pact.io/implementation_guides/jvm/consumer
                        PactDslJsonArray.arrayEachLike()
                                .integerType(USER_ID_PARAM_NAME, USER_ID_EXAMPLE_VALUE)
                                .stringType("userName", "Pactman")
                                .eachLike("skillDtos")
                                .integerType("skillId", 202)
                                .stringType("skillName", "Creating Pact Consumer Tests")
                                .closeArray()
                                .closeObject()
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact_GetAllUsersWithProviderState")
    public void testCreatePact_GetAllUsersWithProviderState() {
        List<UserDTO> responseOfMockServer = assertDoesNotThrow(() -> userController.getAllUsers());
        assertFalse(responseOfMockServer.isEmpty(), "Response must not be empty, because we defined the body, having an array with one example dto.");
        assertEquals(String.valueOf(USER_ID_EXAMPLE_VALUE), responseOfMockServer.get(0).getUserId());
    }

}
