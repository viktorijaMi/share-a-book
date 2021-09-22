package mk.ukim.finki.emt.authentication.rest;

import mk.ukim.finki.emt.authentication.domain.exceptions.InvalidCredentialsException;
import mk.ukim.finki.emt.authentication.domain.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.authentication.service.UserService;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    public final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public UserId registerUser(@RequestBody UserRegisterForm userRegisterForm){
        return this.userService.register(userRegisterForm);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserLoginForm userLoginForm){
        User user = this.userService.login(userLoginForm);
        return user;
    }

    @GetMapping("/user-address/{id}")
    public Address getUserAddress(@PathVariable UserId id) {
        return this.userService.findById(id).getAddress();
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Username already exists")
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public Map<String, String> usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException ex) {
        return  Map.of("errorMessage", ex.getMessage());
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Passwords do not match")
    @ExceptionHandler(PasswordDoNotMatchException.class)
    public Map<String, String> passwordsDoNotMatchExceptionHandler(PasswordDoNotMatchException ex) {
        return Map.of("errorMessage", ex.getMessage());
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Invalid username or password")
    @ExceptionHandler(InvalidCredentialsException.class)
    public Map<String, String> invalidCredentialsExceptionHandler(InvalidCredentialsException ex) {
        return Map.of("errorMessage", ex.getMessage());
    }
}
