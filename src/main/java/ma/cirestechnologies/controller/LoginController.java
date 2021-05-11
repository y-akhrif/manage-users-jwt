package ma.cirestechnologies.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ma.cirestechnologies.dto.LoginDTO;
import ma.cirestechnologies.dto.TokenDTO;
import ma.cirestechnologies.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    public UsersService usersSericeImpl;

    @PostMapping("/auth")
    @ApiOperation(value = "${LoginController.login}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public TokenDTO login(
            @RequestBody LoginDTO loginDTO)  {


        return usersSericeImpl.login(loginDTO.getUsername(), loginDTO.getPassword());

    }
}
