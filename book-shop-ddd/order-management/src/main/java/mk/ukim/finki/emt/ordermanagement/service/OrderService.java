package mk.ukim.finki.emt.ordermanagement.service;

import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.model.Order;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItem;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {
    OrderId placeOrder(OrderId orderId, Currency currency);

    List<Order> findAll();

    Order findById(OrderId id);

    OrderId createNewOrder();

    OrderItem addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistsException;

    Money changeTotalCurrency(OrderId orderId,Currency currency);

    Set<OrderItem> findAllOrderItemsById(OrderId id);

    void increaseQuantity(OrderId orderId, OrderItemId orderItemId);

    void decreaseQuantity(OrderId orderId, OrderItemId orderItemId);

    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistsException, OrderItemIdNotExistsException;
}
