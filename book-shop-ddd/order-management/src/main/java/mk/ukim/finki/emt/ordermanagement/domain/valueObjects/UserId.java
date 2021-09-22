package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

public class UserId extends DomainObjectId {
    private UserId() {
        super(PublisherId.randomId(PublisherId.class).getId());
    }

    @JsonCreator
    public UserId(@JsonProperty("id") String uuid) {
        super(uuid);
    }

    public static UserId of(String uuid) {
        UserId userId = new UserId(uuid);
        return userId;
    }
}
