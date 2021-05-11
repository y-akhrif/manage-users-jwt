package ma.cirestechnologies.service;

import ma.cirestechnologies.model.User;
import ma.cirestechnologies.dto.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface UsersService {

    TokenDTO login(String username, String password);

    List<User> generateUsers(int count);

    Optional<User> findUser(HttpServletRequest req);

    Optional<User> findUser(String username);

    boolean saveUsers(MultipartFile file);

}
