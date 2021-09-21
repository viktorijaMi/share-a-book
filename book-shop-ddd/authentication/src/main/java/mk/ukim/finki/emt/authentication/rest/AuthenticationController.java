package mk.ukim.finki.emt.authentication.rest;

import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.authentication.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    public final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegisterForm userRegisterForm){
        return this.userService.register(userRegisterForm);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserLoginForm userLoginForm){
        return this.userService.login(userLoginForm);
    }
}
