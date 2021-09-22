package mk.ukim.finki.emt.authentication.service.impl;

import mk.ukim.finki.emt.authentication.domain.exceptions.InvalidCredentialsException;
import mk.ukim.finki.emt.authentication.domain.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UserNotFoundException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.emt.sharedkernel.domain.user.Role;
import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.domain.repository.UserRepository;
import mk.ukim.finki.emt.authentication.domain.valueObjects.Password;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.authentication.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;

        public UserServiceImpl(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public User findById(UserId userId) {
                return userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s was not found", userId.getId())));
        }

        @Override
        public UserId register(UserRegisterForm userRegisterForm) {
                if (this.userRepository.existsByUsername(userRegisterForm.getUsername())){
                        throw new UsernameAlreadyExistsException(String.format("User with username %s already exists", userRegisterForm.getUsername()));
                }
                if (!userRegisterForm.getPassword().equals(userRegisterForm.getConfirmPassword())){
                        throw new PasswordDoNotMatchException("Passwords do not match!");
                }

                User user = User.build(userRegisterForm.getUsername(), userRegisterForm.getEmail(), Role.valueOf(userRegisterForm.getRole()), userRegisterForm.getAddress(), new Password(userRegisterForm.getPassword()));
                userRepository.save(user);
                return user.getId();
        }

        @Override
        public User login(UserLoginForm userLoginForm) {
                if (this.userRepository.findByUsername(userLoginForm.getUsername()).isEmpty()){
                        throw new InvalidCredentialsException("Invalid username or password");
                }

                User user = this.userRepository.findByUsername(userLoginForm.getUsername()).get();

                if (!user.getPassword().getPassword().equals(userLoginForm.getPassword())){
                        throw new InvalidCredentialsException("Invalid username or password");
                }

                return user;
        }
}