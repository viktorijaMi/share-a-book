package mk.ukim.finki.emt.authentication.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class UserId extends DomainObjectId {
    private UserId() {
        super(UserId.randomId(UserId.class).getId());
    }

    @JsonCreator
    public UserId(@JsonProperty("id") @NonNull String uuid) {
        super(uuid);
    }

    public static UserId of(String uuid) {
        UserId userId = new UserId(uuid);
        return userId;
    }
}
