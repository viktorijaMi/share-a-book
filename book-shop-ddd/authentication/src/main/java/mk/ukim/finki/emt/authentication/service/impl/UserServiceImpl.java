package mk.ukim.finki.emt.authentication.service.impl;

import mk.ukim.finki.emt.authentication.domain.exceptions.InvalidCredentialsException;
import mk.ukim.finki.emt.authentication.domain.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.emt.authentication.domain.model.Role;
import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.repository.UserRepository;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.authentication.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public User register(UserRegisterForm userRegisterForm) {
                if (this.userRepository.existsByUsername(userRegisterForm.getUsername())){
                        throw new UsernameAlreadyExistsException(String.format("User with username %s already exists", userRegisterForm.getUsername()));
                }
                if (userRegisterForm.password.equals(userRegisterForm.getPassword())){
                        throw new PasswordDoNotMatchException("Passwords do not match!");
                }

                User user = User.build(userRegisterForm.getUsername(), userRegisterForm.getEmail(), Role.valueOf(userRegisterForm.getRole()), userRegisterForm.getBillingAddress(), userRegisterForm.getPassword());
                return user;
        }

        @Override
        public User login(UserLoginForm userLoginForm) {
                if (this.userRepository.findByUsername(userLoginForm.getUsername()).isEmpty()){
                        throw new InvalidCredentialsException("Invalid username or password");
                }

                User user = this.userRepository.findByUsername(userLoginForm.getUsername()).get();

                if (!user.getPassword().equals(userLoginForm.getPassword())){
                        throw new InvalidCredentialsException("Invalid username or password");
                }

                return user;
        }
}