package mk.ukim.finki.emt.authentication.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class UserId extends DomainObjectId {
    protected UserId(@NonNull String uuid) {
        super(uuid);
    }
}
