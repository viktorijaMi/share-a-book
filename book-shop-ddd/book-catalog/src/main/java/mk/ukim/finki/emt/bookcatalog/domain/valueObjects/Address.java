package mk.ukim.finki.emt.bookcatalog.domain.valueObjects;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address implements ValueObject {

    private final String street;

    private final int streetNumber;

    private final String city;

    private final String country;

    protected Address() {
        this.street = this.city = this.country = "";
        this.streetNumber = 0;
    }

    public Address(@NonNull String street,
                   @NonNull int streetNumber,
                   @NonNull String city,
                   @NonNull String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
    }
}
