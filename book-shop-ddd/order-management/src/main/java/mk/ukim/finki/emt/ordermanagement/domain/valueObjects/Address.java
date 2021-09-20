package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements ValueObject {
    private final String street;

    private final int streetNumber;

    private final String city;

    private final String country;

    protected Address() {
        this.street = this.city = this.country = "";
        this.streetNumber = 0;
    }

    @JsonCreator
    public Address(@JsonProperty("street") String street,
                   @JsonProperty("streetNumber") int streetNumber,
                   @JsonProperty("city") String city,
                   @JsonProperty("country") String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
    }
}
