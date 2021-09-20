package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

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

    public OrderItemRemoved(String topic, String bookId, int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_REMOVED);
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
