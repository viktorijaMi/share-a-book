package mk.ukim.finki.emt.authentication.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.authentication.config.JWTTokenHelper;
import mk.ukim.finki.emt.authentication.domain.exceptions.InvalidCredentialsException;
import mk.ukim.finki.emt.authentication.domain.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginResponse;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.authentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    public final UserService userService;
    public final AuthenticationManager authenticationManager;
    public final JWTTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginForm userLoginForm) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginForm.getUsername(), userLoginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user=(User)authentication.getPrincipal();
        String jwtToken=jwtTokenHelper.generateToken(user.getUsername());

        UserLoginResponse response=new UserLoginResponse();
        response.setToken(jwtToken);

        this.userService.login(userLoginForm);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public UserId registerUser(@RequestBody UserRegisterForm userRegisterForm){
        return this.userService.register(userRegisterForm);
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
