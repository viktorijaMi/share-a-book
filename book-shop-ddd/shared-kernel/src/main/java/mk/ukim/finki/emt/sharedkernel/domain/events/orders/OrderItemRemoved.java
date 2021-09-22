package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class OrderItemRemoved extends DomainEvent {

    private String bookId;
    private int quantity;

    public OrderItemRemoved(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_REMOVED);
    }

    public OrderItemRemoved(
            @JsonProperty("bookId") String bookId,
            @JsonProperty("quantity") int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_REMOVED);
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
