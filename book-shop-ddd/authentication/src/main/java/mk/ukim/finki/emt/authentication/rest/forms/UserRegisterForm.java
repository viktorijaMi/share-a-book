package mk.ukim.finki.emt.authentication.rest.forms;

import lombok.Data;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;

@Data
public class UserRegisterForm {

    public String username;

    public String email;

    public String role;

    public Address address;

    public String password;

    public String confirmPassword;
}
