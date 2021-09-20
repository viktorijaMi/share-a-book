package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.BookId;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.UserId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    private Instant orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    @Column(name="order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemsList;

//    @AttributeOverride(name="id", column = @Column(name = "user_id", nullable=false))
//    private UserId userId;

    public Order() {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = Instant.now();
        this.orderState = OrderState.CREATED;
        this.currency = Currency.MKD;
        this.orderItemsList = new HashSet<>();
    }

    public Order(Instant now, @NotNull Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = now;
        this.orderState = OrderState.PROCESSING;
        this.currency = currency;
        this.orderItemsList = new HashSet<>();
    }

    public void setOrderCurrency(Currency c){
        this.currency = c;
    }

    public void changeOrderState(OrderState orderState){
        this.orderState = orderState;
    }

    public Money total() {
        return orderItemsList.stream()
                    .map(OrderItem::subtotal)
                    .reduce(new Money(currency, 0), Money::add);
    }


    public OrderItem addItem(@NonNull Book book, int quantity) {
        Objects.requireNonNull(book, "book must not be null");
        OrderItem item = new OrderItem(book.getId(), book.getPrice(), quantity, book.getName());
        orderItemsList.add(item);
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId, "Order item id must not be null");
        orderItemsList.removeIf(v -> v.getId().equals(orderItemId));
    }
}
