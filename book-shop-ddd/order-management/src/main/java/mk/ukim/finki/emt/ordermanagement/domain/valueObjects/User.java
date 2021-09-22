package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Value;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;
import mk.ukim.finki.emt.sharedkernel.domain.user.Role;

@Getter
public class User implements ValueObject {

    private final UserId id;
    private final String username;
    private final String email;
    private final Role role;
    private final Address address;

    public User() {
        this.id = UserId.randomId(UserId.class);
        this.username = "";
        this.email = "";
        this.role = Role.ROLE_USER;
        this.address = new Address("", 0, "", "");
    }
    @JsonCreator
    public User(
            @JsonProperty("id") UserId userId,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("role") String role,
            @JsonProperty("address") Address address
    ) {
        this.id = userId;
        this.username = username;
        this.email = email;
        this.role = Role.valueOf(role);
        this.address = address;
    }


}
