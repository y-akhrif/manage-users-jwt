package ma.cirestechnologies.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import ma.cirestechnologies.model.Role;
import ma.cirestechnologies.model.User;
import ma.cirestechnologies.security.JwtTokenProvider;
import ma.cirestechnologies.dto.TokenDTO;
import ma.cirestechnologies.exception.CustomException;
import ma.cirestechnologies.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    UsersRepository usersRepository;

    public TokenDTO login(String username, String password) {
        User user = usersRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public List<User> generateUsers(int count) {
        Faker faker = new Faker();
        List<User> listOfUserEntities = new ArrayList<>();
        for (int i=0;i<count; i++){
            User userEntity = new User();
            listOfUserEntities.add(userEntity);
            userEntity.setFirstName(faker.name().firstName());
            userEntity.setLastName(faker.name().lastName());
            userEntity.setBirthDate(faker.date().birthday());
            userEntity.setCountry(faker.country().name());
            userEntity.setCity(faker.country().capital());
            userEntity.setAvatar(faker.avatar().image());
            userEntity.setCompany(faker.company().name());
            userEntity.setJobPosition(faker.job().position());
            userEntity.setMobile(faker.phoneNumber().cellPhone());
            userEntity.setUsername(faker.name().username());
            userEntity.setPassword(faker.internet().password(6, 10));
            userEntity.setEmail(faker.internet().emailAddress());
            userEntity.setRole(this.getUserRole());
        }

        return listOfUserEntities;
    }

    @Override
    public boolean saveUsers(MultipartFile file){
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            User[] listOfUserEntities =  objectMapper. readValue(content, User[].class);
            for (int i = 0; i< listOfUserEntities.length; i++){
                User user = listOfUserEntities[i];
                user.setPassword( passwordEncoder.encode(user.getPassword()));
                System.out.println(user.getEmail());
                System.out.println(user.getPassword());
                usersRepository.save(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    public Optional<User> findUser(HttpServletRequest req) {
        return usersRepository.findByEmail(jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveToken(req)));
    }

    public Optional<User> findUser(String username) {
        return usersRepository.findByUsernameOrEmail(username, username);
    }

    Role getUserRole(){
        int r = (int) (Math.random()*2);
        Role name = new Role [] {Role.ROLE_USER,Role.ROLE_ADMIN}[r];
        return name;
    }

}
