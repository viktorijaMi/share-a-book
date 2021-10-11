package mk.ukim.finki.emt.authentication.rest.forms;

import mk.ukim.finki.emt.authentication.domain.model.Role;

public class UserForm {

    private String username;
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
