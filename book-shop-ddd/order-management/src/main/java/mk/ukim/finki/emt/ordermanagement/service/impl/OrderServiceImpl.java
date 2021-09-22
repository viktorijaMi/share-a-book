package mk.ukim.finki.emt.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemAlreadyExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdNotExistsException;
import mk.ukim.finki.emt.ordermanagement.domain.model.*;
import mk.ukim.finki.emt.ordermanagement.domain.repository.OrderRepository;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.User;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.UserId;
import mk.ukim.finki.emt.ordermanagement.service.OrderService;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.UserForm;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.OrderItemRemoved;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderId orderId,Currency currency) {
        Order newOrder = this.findById(orderId);
        newOrder.setOrderCurrency(currency);
        newOrder.changeOrderState(OrderState.PROCESSING);
        return newOrder.getId();
    }


    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByUserId(UserId userId) {
        return this.orderRepository.findAllByCreatedById(userId);
    }


    @Override
    public Order findById(OrderId id) {
        return this.orderRepository.findById(id).orElseThrow(OrderIdNotExistsException::new);
    }

    @Override
    public OrderId createNewOrder(UserForm userForm) {
        Order order = new Order(userForm.getId(), userForm.getUsername(), userForm.getAddress());
        this.orderRepository.save(order);
        return order.getId();
    }


    @Override
    public OrderItem addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistsException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        if(order.getOrderItemsList().stream()
                .anyMatch(orderItem -> orderItem.getBookName().equals(orderItemForm.getBook().getName()))){
            throw new OrderItemAlreadyExistsException(String.format("Book with name %s already in cart", orderItemForm.getBook().getName()));
        } else {
            OrderItem orderItem = order.addItem(orderItemForm.getBook(), orderItemForm.getQuantity());
            orderRepository.saveAndFlush(order);
            domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getBook().getId().getId(), orderItemForm.getQuantity()));
            return orderItem;
        }
    }

    @Override
    public Money changeTotalCurrency(OrderId orderId,Currency currency) {
        Order order = this.findById(orderId);
        if (currency.equals(Currency.EUR)){
            return order.getTotal().mkdToEur();
        } else {
            return order.getTotal();
        }
    }


    @Override
    public Set<OrderItem> findAllOrderItemsById(OrderId id) {
        Order order = this.orderRepository.findById(id).orElseThrow(OrderIdNotExistsException::new);
        return order.getOrderItemsList();
    }


    @Override
    public void increaseQuantity(OrderId orderId, OrderItemId orderItemId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        OrderItem orderItem = order.getOrderItemsList()
                .stream()
                .filter(item -> item.getId().getId().equals(orderItemId.getId()))
                .findFirst().orElseThrow(OrderItemIdNotExistsException::new);
        this.deleteItem(order.getId(), orderItem.getId());
        orderItem.increaseQuantity();
        order.getOrderItemsList().add(orderItem);
        order.setTotal();
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public void decreaseQuantity(OrderId orderId, OrderItemId orderItemId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        OrderItem orderItem = order.getOrderItemsList()
                .stream()
                .filter(item -> item.getId().getId().equals(orderItemId.getId()))
                .findFirst().orElseThrow(OrderItemIdNotExistsException::new);
        this.deleteItem(order.getId(), orderItem.getId());
        orderItem.decreaseQuantity();
        order.getOrderItemsList().add(orderItem);
        order.setTotal();
        this.orderRepository.saveAndFlush(order);
    }


    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistsException, OrderItemIdNotExistsException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        OrderItem orderItem = order.getOrderItemsList()
                .stream()
                .filter(orderItem1 -> orderItem1.getId().getId().equals(orderItemId.getId())).findFirst().orElseThrow(OrderItemIdNotExistsException::new);
        order.removeItem(orderItemId);
        domainEventPublisher.publish(new OrderItemRemoved(orderItem.getBookId().getId(), orderItem.getQuantity()));
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void cancelOrder(OrderId orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistsException::new);
        order.changeOrderState(OrderState.CANCELLED);
        orderRepository.saveAndFlush(order);
    }
}
