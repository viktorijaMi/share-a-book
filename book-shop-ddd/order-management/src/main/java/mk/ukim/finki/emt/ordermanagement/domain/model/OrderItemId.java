package mk.ukim.finki.emt.ordermanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class OrderItemId extends DomainObjectId {
    private OrderItemId() {
        super(OrderItemId.randomId(OrderItemId.class).getId());
    }

    @JsonCreator
    public OrderItemId(@NonNull String uuid) {
        super(uuid);
    }

    public static OrderItemId of(String uuid) {
        OrderItemId orderItemId = new OrderItemId(uuid);
        return orderItemId;
    }
}
