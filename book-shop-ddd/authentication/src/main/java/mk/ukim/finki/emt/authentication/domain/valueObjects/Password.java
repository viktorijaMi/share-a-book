package mk.ukim.finki.emt.authentication.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Password implements ValueObject {

    private final String password;

    protected Password() {
        this.password = "";
    }

    public Password(@NonNull String password) {
        this.password = password;
    }
}
