package consumer.user.service;

import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import java.util.Collections;
import java.util.List;
import org.example.consumer.user.service.controller.UserClient;
import org.example.consumer.user.service.controller.UserController;
import org.example.consumer.user.service.schema.SkillDTO;
import org.example.consumer.user.service.schema.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserController.class, UserClient.class})
@AutoConfigureWebClient
@PactTestFor(port = "8083")
@TestPropertySource(properties = {
        // overrides default spring property in application.properties
        "user.service.url=http://localhost:8083/user",
        // enable if test fails to get more details in log
        // "logging.level.au.com.dius.pact=DEBUG"
})
@ExtendWith(PactConsumerTestExt.class)
public class UserControllerConsumerPactTest {

    private static final String USER_ID_PARAM = "userId";
    public static final String USER_NAME_PARAM = "userName";

    public static final int USER_ID = 101;
    public static final String USER_NAME = "PactCat";
    public static final int SKILL_ID = 456;
    public static final String SKILL_NAME = "Creating Pact Consumer Tests";
    public static final String SKILL_DTOS_PARAM = "skillDtos";
    public static final String SKILL_ID_PARAM = "skillId";
    public static final String SKILL_NAME_PARAM = "skillName";

    @Autowired
    private UserController userController;

    @Autowired
    private UserClient userClient;

    private UserDTO createUserDTO() {
        UserDTO exampleUserDTO = new UserDTO();
        exampleUserDTO.setUserId(String.valueOf(USER_ID));
        exampleUserDTO.setUserName(USER_NAME);

        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setSkillId(String.valueOf(SKILL_ID));
        skillDTO.setSkillName(SKILL_NAME);

        exampleUserDTO.setSkillDtos(Collections.singletonList(skillDTO));
        return exampleUserDTO;
    }

    @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
    public RequestResponsePact createPact_GetAllUsers(PactDslWithProvider builder) {
        return builder
                .uponReceiving("getAllUsers test interaction response")
                .path("/user/getAllUsers")
                .method("GET")
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(
                        // https://docs.pact.io/implementation_guides/jvm/consumer
                        PactDslJsonArray.arrayEachLike()
                                .integerType(USER_ID_PARAM, USER_ID)
                                .stringType(USER_NAME_PARAM, USER_NAME)
                                .eachLike(SKILL_DTOS_PARAM)
                                .integerType(SKILL_ID_PARAM, SKILL_ID)
                                .stringType(SKILL_NAME_PARAM, SKILL_NAME)
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

    @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
    public RequestResponsePact createPact_GetAllUsersWithProviderState(PactDslWithProvider builder) {

        return builder
                .given("User with user id ${" + USER_ID_PARAM + "} exists", USER_ID_PARAM, USER_ID)
                .uponReceiving("getAllUsers test interaction response for user with " + USER_ID_PARAM + " = " + USER_ID)
                .path("/user/getAllUsers")
                .method("GET")
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(
                        // https://docs.pact.io/implementation_guides/jvm/consumer
                        PactDslJsonArray.arrayEachLike()
                                .integerType(USER_ID_PARAM, USER_ID)
                                .stringType(USER_NAME_PARAM, USER_NAME)
                                .eachLike(SKILL_DTOS_PARAM)
                                .integerType(SKILL_ID_PARAM, SKILL_ID)
                                .stringType(SKILL_NAME_PARAM, SKILL_NAME)
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
        assertEquals(String.valueOf(USER_ID), responseOfMockServer.get(0).getUserId());
    }

    @Pact(provider = "PactDemoProvider", consumer = "PactDemoConsumer")
    public RequestResponsePact createPact_GetUserById(PactDslWithProvider builder) {
        return builder
                .uponReceiving("getAllUsers test interaction response")
                .path("/user/" + USER_ID)
                .method("GET")
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(
                        // https://docs.pact.io/implementation_guides/jvm/consumer
                        PactDslJsonArray.arrayEachLike()
                                .integerType(USER_ID_PARAM, USER_ID)
                                .stringType(USER_NAME_PARAM, USER_NAME)
                                .eachLike(SKILL_DTOS_PARAM)
                                .integerType(SKILL_ID_PARAM, SKILL_ID)
                                .stringType(SKILL_NAME_PARAM, SKILL_NAME)
                                .closeArray()
                                .closeObject()
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact_GetUserById")
    public void testCreatePact_GetUserById() {
        UserDTO responseOfMockServer = assertDoesNotThrow(() -> userController.getUser(String.valueOf(USER_ID)));
        assertNotNull(responseOfMockServer, "Response must not be empty, because we defined the body, containing one example dto.");
        assertEquals(createUserDTO(), responseOfMockServer, "Response body contained a different user dto as expected.");
    }

    // TODO createPact_GetUserById provider state

}
