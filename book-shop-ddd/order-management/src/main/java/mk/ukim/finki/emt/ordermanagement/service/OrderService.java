package mk.ukim.finki.emt.ordermanagement.service;

import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.model.Order;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItem;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.User;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.UserId;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.UserForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {
    /**
     * This method changes the order state to processing
     * @param orderId
     * @param currency
     * @return
     */
    OrderId placeOrder(OrderId orderId, Currency currency);

    /**
     * This method returns all orders
     * @return
     */
    List<Order> findAll();

    /**
     * This method returns all orders made by the user with id userId
     * @param userId
     * @return
     */
    List<Order> findAllByUserId(UserId userId);

    /**
     * This method returns the order with the given id
     * @param id
     * @return
     */
    Order findById(OrderId id);

    /**
     * This method creates a new empty form(without order items) for the user specified in the user form
     * @param userForm
     * @return
     */
    OrderId createNewOrder(UserForm userForm);

    /**
     * This method adds an order item (a book) in the order items list of the order with id orderId
     * @param orderId
     * @param orderItemForm
     * @return
     * @throws OrderIdNotExistsException
     */
    OrderItem addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistsException;

    /**
     * This method changes the currency of the total value for the order with id orderId
     * @param orderId
     * @param currency
     * @return
     */
    Money changeTotalCurrency(OrderId orderId,Currency currency);

    /**
     * This method returns a set of order items for the order with the given id
     * @param id
     * @return
     */
    Set<OrderItem> findAllOrderItemsById(OrderId id);

    /**
     * This method increases the quantity of the ordered item with id orderItemId for the order with id orderId
     * @param orderId
     * @param orderItemId
     */
    void increaseQuantity(OrderId orderId, OrderItemId orderItemId);

    /**
     * This method decreases the quantity of the ordered item with id orderItemId for the order with id orderId
     * @param orderId
     * @param orderItemId
     */
    void decreaseQuantity(OrderId orderId, OrderItemId orderItemId);

    /**
     * This method deletes the item with id orderItemId from the order with id orderId
     * @param orderId
     * @param orderItemId
     */
    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistsException, OrderItemIdNotExistsException;

    /**
     * This method changes the order state to cancelled
     * @param orderId
     */
    void cancelOrder(OrderId orderId);
}
