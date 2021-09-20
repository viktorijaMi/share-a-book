package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.Book;
import mk.ukim.finki.emt.ordermanagement.domain.valueObjects.BookId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_item")
public class OrderItem extends AbstractEntity<OrderItemId> {

    @AttributeOverrides({
            @AttributeOverride(name="amount", column = @Column(name = "itemPrice_amount")),
            @AttributeOverride(name="currency", column = @Column(name = "itemPrice_currency")),
    })
    private Money itemPrice;

    @Column(nullable = false)
    private int quantity;

    @AttributeOverride(name="id", column = @Column(name = "book_id", nullable=false))
    private BookId bookId;

    private String bookName;

    public Money subtotal(){
        return itemPrice.multiply(quantity);
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    private OrderItem(){
        super(DomainObjectId.randomId(OrderItemId.class));
        itemPrice = Money.valueOf(Currency.MKD, 0);
        quantity = 0;
        bookId = BookId.randomId(BookId.class);
        bookName = "";
    }

    public OrderItem(@NonNull BookId bookId, @NonNull Money itemPrice, int quantity, @NonNull String bookName) {
        super(DomainObjectId.randomId(OrderItemId.class));
        this.bookId = bookId;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.bookName = bookName;
    }
}
