package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

@Getter
public class Publisher implements ValueObject {

    private final PublisherId id;
    private final String name;
    private final Address address;

    private Publisher() {
        this.name = "";
        this.id = PublisherId.randomId(PublisherId.class);
        this.address = null;
    }

    @JsonCreator
    public Publisher(@JsonProperty("id") PublisherId id,
                   @JsonProperty("name") String name,
                   @JsonProperty("address") Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
