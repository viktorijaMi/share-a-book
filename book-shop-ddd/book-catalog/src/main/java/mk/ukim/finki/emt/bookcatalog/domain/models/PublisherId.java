package mk.ukim.finki.emt.bookcatalog.domain.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class PublisherId extends DomainObjectId {
    private PublisherId() {
        super(PublisherId.randomId(PublisherId.class).getId());
    }

    @JsonCreator
    public PublisherId(@NonNull String uuid) {
        super(uuid);
    }

    public static PublisherId of(String uuid) {
        PublisherId publisherId = new PublisherId(uuid);
        return publisherId;
    }
}
