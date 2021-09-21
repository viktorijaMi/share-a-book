package mk.ukim.finki.emt.authentication.rest.forms;

import lombok.Data;
import mk.ukim.finki.emt.authentication.domain.model.Role;
import mk.ukim.finki.emt.authentication.domain.valueObjects.Password;
import mk.ukim.finki.emt.sharedkernel.domain.base.Address;

@Data
public class UserRegisterForm {

    public String username;

    public String email;

    public String role;

    public Address billingAddress;

    public Password password;

    public Password confirmPassword;
}
