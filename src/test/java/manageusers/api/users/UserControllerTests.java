package manageusers.api.users;

import ma.cirestechnologies.MainApplication;
import ma.cirestechnologies.controller.LoginController;
import ma.cirestechnologies.controller.UserController;
import ma.cirestechnologies.dto.LoginDTO;
import ma.cirestechnologies.dto.TokenDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes=MainApplication.class)
public class UserControllerTests {

    @Autowired
    UserController userController;

    @Autowired
    LoginController loginController;

    @Autowired
    private HttpServletRequest request;

    @Test
    @Order(1)
    public void generateUsersFiles_ShouldNotBeNull() throws IOException {

        ResponseEntity<Resource> resource =  userController.generateUsers(3);

        assertNotNull(resource);

    }

    @Test
    @Order(2)
    public void executebatch_ShouldBeOk() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("listOfUsers.json", new FileInputStream(new File("src/test/resources/listOfUsers.json")));

        ResponseEntity responseEntity = userController.saveUsers( multipartFile);
        assertEquals(  "200 OK", responseEntity.getStatusCode().toString());

    }

    @Test
    @Order(3)
    public void auth_ShouldBeOk()  {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("cyrstal.becker");
        loginDTO.setPassword("uru3lqoan");
        TokenDTO tokenDTO = loginController.login(loginDTO);

        assertNotEquals("", tokenDTO.getAccessToken());
    }
}
