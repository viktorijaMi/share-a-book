package mk.ukim.finki.emt.authentication.domain.model;

import mk.ukim.finki.emt.authentication.domain.valueObjects.Password;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractEntity<UserId> {

    private String name;

    private String email;

    @Column(name = "user_role")
    private Role role;

    private Password password;

}
