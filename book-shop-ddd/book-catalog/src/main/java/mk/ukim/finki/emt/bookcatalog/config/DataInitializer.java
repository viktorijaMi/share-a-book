package mk.ukim.finki.emt.bookcatalog.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.bookcatalog.domain.models.Book;
import mk.ukim.finki.emt.bookcatalog.domain.models.Publisher;
import mk.ukim.finki.emt.bookcatalog.domain.repository.BookRepository;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Address;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Category;
import mk.ukim.finki.emt.bookcatalog.xport.rest.BookResource;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {
    private final BookRepository bookRepository;

    @PostConstruct
    public void initData() {
        Publisher p = new Publisher("Feniks", new Address("Kosturski Heroi", 35, "Skopje", "Makedonija"));
        Book b1 = Book.build("Mind's Future", Money.valueOf(Currency.MKD, 750), 5, Category.Health, p, "https://images-na.ssl-images-amazon.com/images/I/81wE8rTmN6L.jpg");
        Book b2 = Book.build("Origin", Money.valueOf(Currency.MKD, 1000), 2, Category.Thriller, p, "https://static01.nyt.com/images/2017/10/05/arts/05bookbrown1/05bookbrown1-superJumbo.jpg");
        if (bookRepository.findAll().isEmpty()) {
            bookRepository.saveAll(Arrays.asList(b1, b2));
        }
    }
}
