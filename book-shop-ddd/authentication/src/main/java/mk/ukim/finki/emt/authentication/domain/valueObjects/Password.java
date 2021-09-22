package mk.ukim.finki.emt.authentication.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Password implements ValueObject {

    private final String password;

    protected Password() {
        this.password = "";
    }

    @JsonCreator
    public Password(@JsonProperty("password") @NonNull String password) {
        this.password = password;
        checkPassword();
    }

    public boolean checkPassword() {
        return this.password.matches(".*[0-9].*") && this.password.matches(".*[a-zA-Z].*]") && this.password.length() >= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
