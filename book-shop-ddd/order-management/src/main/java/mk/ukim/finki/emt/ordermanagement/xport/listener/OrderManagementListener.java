package mk.ukim.finki.emt.ordermanagement.xport.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderState;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.UserId;
import mk.ukim.finki.emt.ordermanagement.service.OrderService;
import mk.ukim.finki.emt.ordermanagement.service.forms.UserForm;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.OrderItemRemoved;
import mk.ukim.finki.emt.sharedkernel.domain.events.user.UserLogged;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderManagementListener {

    private final OrderService orderService;

    @KafkaListener(topics = TopicHolder.TOPIC_USER_LOGGED, groupId = "authentication")
    public void consumeUserLoggedIn(@Payload(required = false)String jsonMessage) {
        try {
            UserLogged event = UserLogged.fromJson(jsonMessage, UserLogged.class);
            orderService.createNewOrder(new UserForm(UserId.of(event.getUserId()), event.getUsername(), event.getUserAddress()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
