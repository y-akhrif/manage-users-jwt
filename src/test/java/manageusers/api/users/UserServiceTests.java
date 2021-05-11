package manageusers.api.users;

import ma.cirestechnologies.MainApplication;
import ma.cirestechnologies.model.User;
import ma.cirestechnologies.service.UsersService;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes=MainApplication.class)
public class UserServiceTests {

    @Autowired
    UsersService usersServiceImp;


    @Test
    @Order(1)
    public void saveUsers_ShouldBeOk() throws IOException, JSONException {

        MultipartFile multipartFile = new MockMultipartFile("listOfUsers.json", new FileInputStream(new File("src/test/resources/listOfUsers.json")));

        assertTrue(  "200 OK", usersServiceImp.saveUsers( multipartFile));

    }


    @Test
    @Order(2)
    public void userFinddMe_ShouldBeOk() {


        Optional<User> user = usersServiceImp.findUser("cyrstal.becker");

        assertEquals("Marvin", user.get().getFirstName());
        assertEquals("Hamill", user.get().getLastName());
        assertEquals("1984-11-10 14:46:45.914", user.get().getBirthDate().toString());
        assertEquals("Podgorica", user.get().getCity());
        assertEquals("Bulgaria", user.get().getCountry());
        assertEquals("https://s3.amazonaws.com/uifaces/faces/twitter/findingjenny/128.jpg", user.get().getAvatar());
        assertEquals("Kuphal LLC", user.get().getCompany());
        assertEquals("Architect", user.get().getJobPosition());
        assertEquals("148.383.2638", user.get().getMobile());
        assertEquals("sheldon.treutel@gmail.com", user.get().getEmail());






        assertEquals("cyrstal.becker", user.get().getUsername());

        assertEquals("cyrstal.becker", user.get().getUsername());
    }
}
