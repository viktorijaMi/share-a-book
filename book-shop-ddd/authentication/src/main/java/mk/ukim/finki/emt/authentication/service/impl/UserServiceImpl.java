package mk.ukim.finki.emt.authentication.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.authentication.domain.exceptions.InvalidCredentialsException;
import mk.ukim.finki.emt.authentication.domain.exceptions.PasswordDoNotMatchException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UserNotFoundException;
import mk.ukim.finki.emt.authentication.domain.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.emt.authentication.domain.model.Role;
import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.domain.repository.UserRepository;
import mk.ukim.finki.emt.authentication.domain.valueObjects.Password;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.authentication.service.UserService;
import mk.ukim.finki.emt.sharedkernel.domain.events.user.UserLogged;
import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final DomainEventPublisher domainEventPublisher;

        @Override
        public User findById(UserId userId) {
                return userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s was not found", userId.getId())));
        }

        @Override
        public User findByUsername(String username) {
                return this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s was not found", username)));
        }

        @Override
        public UserId register(UserRegisterForm userRegisterForm) {
                if (this.userRepository.existsByUsername(userRegisterForm.getUsername())){
                        throw new UsernameAlreadyExistsException(String.format("User with username %s already exists", userRegisterForm.getUsername()));
                }
                if (!userRegisterForm.getPassword().equals(userRegisterForm.getConfirmPassword())){
                        throw new PasswordDoNotMatchException("Passwords do not match!");
                }
                String password = passwordEncoder.encode(userRegisterForm.getPassword());
                User user = User.build(userRegisterForm.getUsername(), userRegisterForm.getEmail(), Role.valueOf(userRegisterForm.getRole()), userRegisterForm.getAddress(), new Password(password));
                userRepository.save(user);
                return user.getId();
        }

        @Override
        public User login(UserLoginForm userLoginForm) {
                if (this.userRepository.findByUsername(userLoginForm.getUsername()).isEmpty()){
                        throw new InvalidCredentialsException("Invalid username or password");
                }

                User user = this.userRepository.findByUsername(userLoginForm.getUsername()).get();

                if (!passwordEncoder.matches(userLoginForm.getPassword(), user.getPassword())){
                        throw new InvalidCredentialsException("Invalid username or password");
                }
                domainEventPublisher.publish(new UserLogged(user.getId().getId(), user.getUsername(), user.getAddress()));
                return user;
        }

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
        }
}