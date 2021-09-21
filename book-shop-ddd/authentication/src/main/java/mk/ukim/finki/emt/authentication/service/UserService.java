package mk.ukim.finki.emt.authentication.service;

import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;
import mk.ukim.finki.emt.sharedkernel.domain.base.Address;

import java.util.Optional;

public interface UserService {

    User register(UserRegisterForm userRegisterForm);

    User login(UserLoginForm userLoginForm);
}
