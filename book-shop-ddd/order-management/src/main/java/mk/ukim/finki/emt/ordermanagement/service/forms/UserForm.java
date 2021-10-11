package mk.ukim.finki.emt.ordermanagement.service.forms;

import lombok.Data;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.UserId;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;

import javax.validation.constraints.NotNull;

@Data
public class UserForm {

    @NotNull
    private UserId id;

    @NotNull
    private String username;

    @NotNull
    private Address address;

    public UserForm(UserId id, String username, Address userAddress) {
        this.id = id;
        this.username = username;
        this.address = userAddress;
    }
}
