package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

public class UserId extends DomainObjectId {
    protected UserId(@NonNull String uuid) {
        super(uuid);
    }
}
