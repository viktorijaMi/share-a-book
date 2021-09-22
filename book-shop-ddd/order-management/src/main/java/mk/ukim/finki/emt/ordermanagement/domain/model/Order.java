package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.BookId;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.User;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.UserId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import mk.ukim.finki.emt.sharedkernel.domain.user.Address;

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

    @AttributeOverrides({
            @AttributeOverride(name="amount", column = @Column(name = "total_amount")),
            @AttributeOverride(name="currency", column = @Column(name = "total_currency")),
    })
    private Money total;

    @AttributeOverride(name="id", column = @Column(name = "createdBy_id", nullable=false))
    private UserId createdById;

    private String createdBy_username;

    @AttributeOverrides({
            @AttributeOverride(name="street", column = @Column(name = "address_street")),
            @AttributeOverride(name="streetNumber", column = @Column(name = "address_street_number")),
            @AttributeOverride(name="city", column = @Column(name = "address_city")),
            @AttributeOverride(name="country", column = @Column(name = "address_country")),
    })
    private Address createdBy_address;

    public Order() {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = Instant.now();
        this.orderState = OrderState.CREATED;
        this.currency = Currency.MKD;
        this.orderItemsList = new HashSet<>();
        this.createdById = null;
        this.createdBy_username = "";
        this.createdBy_address = new Address("", 0, "","");
        setTotal();
    }

    public Order(UserId userId, String username, Address address) {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = Instant.now();
        this.orderState = OrderState.CREATED;
        this.currency = Currency.MKD;
        this.orderItemsList = new HashSet<>();
        this.createdById = userId;
        this.createdBy_username = username;
        this.createdBy_address = address;
        setTotal();
    }


    public Order(Instant now, @NotNull Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.orderDate = now;
        this.orderState = OrderState.PROCESSING;
        this.currency = currency;
        this.orderItemsList = new HashSet<>();
        setTotal();
    }

    public void setOrderCurrency(Currency c){
        this.currency = c;
    }

    public void changeOrderState(OrderState orderState){
        this.orderState = orderState;
    }

    public void setTotal() {
        this.total = orderItemsList.stream()
                    .map(OrderItem::subtotal)
                    .reduce(new Money(currency, 0), Money::add);
    }


    public OrderItem addItem(@NonNull Book book, int quantity) {
        Objects.requireNonNull(book, "book must not be null");
        OrderItem item = new OrderItem(book.getId(), book.getPrice(), quantity, book.getName());
        orderItemsList.add(item);
        setTotal();
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId, "Order item id must not be null");
        orderItemsList.removeIf(v -> v.getId().getId().equals(orderItemId.getId()));
        setTotal();
    }
}
