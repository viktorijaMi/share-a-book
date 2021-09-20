package mk.ukim.finki.emt.bookcatalog.xport.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.bookcatalog.domain.models.BookId;
import mk.ukim.finki.emt.bookcatalog.service.BookService;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.OrderItemRemoved;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookEventListener {

    private final BookService bookService;

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_CREATED, groupId = "bookCatalog")
    public void consumeOrderItemCreatedEvent(@Payload(required = false)String jsonMessage) {
        try {
            OrderItemCreated event = DomainEvent.fromJson(jsonMessage, OrderItemCreated.class);
            bookService.orderItemCreated(BookId.of(event.getBookId()), event.getQuantity());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_REMOVED, groupId = "bookCatalog")
    public void consumeOrderItemRemovedEvent(@Payload(required = false)String jsonMessage) {
        try {
            OrderItemRemoved event = DomainEvent.fromJson(jsonMessage, OrderItemRemoved.class);
            bookService.orderItemRemoved(BookId.of(event.getBookId()), event.getQuantity());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

//    @KafkaListener(topics = TopicHolder.CHANGE_CURRENCY, groupId = "bookCatalog")
//    public void consumeChangeCurrency(@Payload(required = false) String jsonMessage) {
//        try {
//            ChangeCurrency event = DomainEvent.fromJson(jsonMessage, ChangeCurrency.class);
//            bookService.changeCurrency(event.getCurrency());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
}
