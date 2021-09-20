package mk.ukim.finki.emt.bookcatalog.domain.models;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Address;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "publisher")
@Getter
public class Publisher extends AbstractEntity<PublisherId> {

    private String name;

    @AttributeOverrides({
            @AttributeOverride(name="street", column = @Column(name = "address_street")),
            @AttributeOverride(name="streetNumber", column = @Column(name = "address_street_number")),
            @AttributeOverride(name="city", column = @Column(name = "address_city")),
            @AttributeOverride(name="country", column = @Column(name = "address_country")),
    })
    private Address address;

    private Publisher() {
        super(PublisherId.randomId(PublisherId.class));
        this.name = "";
        this.address = new Address("",0,"", "");
    }

    public Publisher(@NonNull String name,
                     @NonNull Address address) {
        this.name = name;
        this.address =address;
    }
}
