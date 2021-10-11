package mk.ukim.finki.emt.bookcatalog.service.form;

import lombok.Data;
import lombok.Getter;
import mk.ukim.finki.emt.bookcatalog.domain.models.Publisher;
import mk.ukim.finki.emt.bookcatalog.domain.models.PublisherId;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Category;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;


@Data
public class BookForm {

    private String bookName;

    private double price;

    private int sales;

    private int quantity;

    private Category category;

    private String publisher;

    private String bookImageUrl;
}
