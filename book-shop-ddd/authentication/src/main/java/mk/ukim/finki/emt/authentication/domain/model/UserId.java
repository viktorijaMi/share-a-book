package mk.ukim.finki.emt.authentication.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class UserId extends DomainObjectId {
    private UserId() {
        super(UserId.randomId(UserId.class).getId());
    }

    public UserId(@NonNull String uuid) {
        super(uuid);
    }

    public static UserId of(String uuid) {
        UserId userId = new UserId(uuid);
        return userId;
    }
}
