package mk.ukim.finki.emt.authentication.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.authentication.domain.valueObjects.Password;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.base.Address;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Table(name = "shop_users")
public class User extends AbstractEntity<UserId> {

    private String username;

    private String email;

    @Column(name = "user_role")
    private Role role;

    @AttributeOverrides({
            @AttributeOverride(name="street", column = @Column(name = "address_street")),
            @AttributeOverride(name="streetNumber", column = @Column(name = "address_street_number")),
            @AttributeOverride(name="city", column = @Column(name = "address_city")),
            @AttributeOverride(name="country", column = @Column(name = "address_country")),
    })
    private Address billingAddress;

    private Password password;

    public User(){
        super(UserId.randomId(UserId.class));
    }

    public static User build(String username, String email, Role role, Address address, Password password){
        User user = new User();
        user.username = username;
        user.email = email;
        user.role = role;
        user.billingAddress = address;
        user.password = password;
        return user;
    }
}
