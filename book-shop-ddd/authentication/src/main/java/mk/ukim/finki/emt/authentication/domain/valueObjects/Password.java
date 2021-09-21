package mk.ukim.finki.emt.authentication.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password implements ValueObject {

    private final String password;

    protected Password() {
        this.password = "";
    }

    public Password(@NonNull String password) {
        this.password = password;
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
