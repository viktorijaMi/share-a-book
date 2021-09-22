package mk.ukim.finki.emt.ordermanagement.service.forms;

import lombok.Data;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;

import javax.validation.constraints.NotNull;

@Data
public class OrderIdsForm {

    @NotNull
    private OrderId orderId;

    @NotNull
    private OrderItemId orderItemId;
}
