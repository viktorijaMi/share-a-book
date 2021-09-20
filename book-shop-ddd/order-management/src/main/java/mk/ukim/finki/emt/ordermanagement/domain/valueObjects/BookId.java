package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class BookId extends DomainObjectId {
    private BookId() {
        super(BookId.randomId(BookId.class).getId());
    }

    @JsonCreator
    public BookId(@JsonProperty("id") String uuid) {
        super(uuid);
    }

    public static BookId of(String uuid) {
        BookId bookId = new BookId(uuid);
        return bookId;
    }
}
