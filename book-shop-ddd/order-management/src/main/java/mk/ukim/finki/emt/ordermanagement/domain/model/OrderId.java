package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class OrderId extends DomainObjectId {
    private OrderId() {
        super(OrderId.randomId(OrderId.class).getId());
    }

    public OrderId(@NonNull String uuid) {
        super(uuid);
    }

    public static OrderId of(String uuid) {
        OrderId orderId = new OrderId(uuid);
        return orderId;
    }
}
