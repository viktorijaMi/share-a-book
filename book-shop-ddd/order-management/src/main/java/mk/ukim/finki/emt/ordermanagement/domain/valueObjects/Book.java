package mk.ukim.finki.emt.ordermanagement.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Getter
public class Book implements ValueObject {

    private final BookId id;
    private final String name;
    private final Money price;
    private final Category category;
    private final Publisher publisher;
    private final int sales;

    private Book() {
        this.id = BookId.randomId(BookId.class);
        this.name = "";
        this.price = Money.valueOf(Currency.MKD, 0);
        this.sales = 0;
        this.publisher = null;
        this.category = null;
    }

    @JsonCreator
    public Book(@JsonProperty("id") BookId id,
                @JsonProperty("bookName") String name,
                @JsonProperty("sales") int sales,
                @JsonProperty("price") Money price,
                @JsonProperty("category") Category category,
                 @JsonProperty("publishedBy") Publisher publisher) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sales = sales;
        this.category = category;
        this.publisher = publisher;
    }
}
