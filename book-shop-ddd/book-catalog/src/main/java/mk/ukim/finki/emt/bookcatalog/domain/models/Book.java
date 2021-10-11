package mk.ukim.finki.emt.bookcatalog.domain.models;

import lombok.Getter;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Category;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Quantity;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Getter
public class Book extends AbstractEntity<BookId> {

    private String bookName;

    private int sales = 0;

    private String bookImageUrl;

    private int quantity;

    @AttributeOverrides({
            @AttributeOverride(name="amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name="currency", column = @Column(name = "price_currency")),
    })
    private Money price;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Publisher publishedBy;

    public Book() {
     super(BookId.randomId(BookId.class));
    }

    public static Book build(String bookName, Money price, int sales,Category category, Publisher publisher, String bookImageUrl, int quantity) {
        Book book = new Book();
        book.bookName = bookName;
        book.price = price;
        book.sales = sales;
        book.category = category;
        book.publishedBy = publisher;
        book.bookImageUrl = bookImageUrl;
        book.quantity = quantity;
        return book;
    }

    public void addSales(int quantity) {
        this.sales += quantity;
    }

    public void removeSales(int quantity) {
        this.sales -= quantity;
    }
}
