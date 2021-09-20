package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;

@Getter
public class OrderItemCreated extends DomainEvent {

    private String bookId;
    private int quantity;

    public OrderItemCreated(){
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
        this.bookId = null;
        this.quantity = 0;
    }

    public OrderItemCreated(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
    }

    public OrderItemCreated(
            @JsonProperty("bookId") String bookId,
            @JsonProperty("quantity") int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
