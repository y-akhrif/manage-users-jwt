package ma.cirestechnologies.repository;


import ma.cirestechnologies.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UsersRepository extends CrudRepository<User, Long> {

    User save(User userEntity);

    Optional<User>  findByUsername(String username);

    Optional<User>  findByEmail(String email);


    Optional<User>  findByUsernameOrEmail(String username, String email);


}
