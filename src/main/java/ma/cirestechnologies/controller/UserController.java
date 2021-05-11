package ma.cirestechnologies.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import ma.cirestechnologies.model.User;
import ma.cirestechnologies.dto.UserDTO;
import ma.cirestechnologies.repository.UsersRepository;
import ma.cirestechnologies.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UsersService usersSericeImpl;

    @Autowired
    public UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value="/generate")
    public ResponseEntity<Resource> generateUsers(@RequestParam int count) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String simpleJSON = mapper.writeValueAsString(usersSericeImpl.generateUsers(count));

        InputStream inputStream = new ByteArrayInputStream(simpleJSON.getBytes());
        InputStreamResource resource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Content-Disposition", "attachment; filename=listOfUsers.json");


        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    @PostMapping(value="/batch",
            consumes = { "multipart/form-data" })
    public ResponseEntity saveUsers( @RequestParam("multipart-file") MultipartFile file){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        if(usersSericeImpl.saveUsers(file)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("File uploaded successfully");
        }else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Error en parsing the JSON file");
        }
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ApiOperation(value = "${UserController.findMe}", response = UserDTO.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserDTO findMe(HttpServletRequest req) {
        User user = usersSericeImpl.findUser(req)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.findUser}", response = UserDTO.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserDTO findUser(@ApiParam("Username") @PathVariable String username) {

        User user = usersSericeImpl.findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }




}
