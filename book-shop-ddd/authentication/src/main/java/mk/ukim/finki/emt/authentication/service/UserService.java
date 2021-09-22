package mk.ukim.finki.emt.authentication.service;

import mk.ukim.finki.emt.authentication.domain.model.User;
import mk.ukim.finki.emt.authentication.domain.model.UserId;
import mk.ukim.finki.emt.authentication.rest.forms.UserLoginForm;
import mk.ukim.finki.emt.authentication.rest.forms.UserRegisterForm;

import java.util.Optional;

public interface UserService {

    /**
     * This method returns the user with id userId
     * @param userId
     * @return
     */
    User findById(UserId userId);

    /**
     * This method returns the userId of the registered user with data from userRegisterForm
     * (further to be implemented with password encoder)
     * @param userRegisterForm
     * @return
     */
    UserId register(UserRegisterForm userRegisterForm);

    /**
     * This method returns the logged in user
     * (further to be implemented with password encoder)
     * @param userLoginForm
     * @return
     */
    User login(UserLoginForm userLoginForm);
}
