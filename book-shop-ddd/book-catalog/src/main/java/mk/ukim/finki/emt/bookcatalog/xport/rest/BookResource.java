package mk.ukim.finki.emt.bookcatalog.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.bookcatalog.config.DataInitializer;
import mk.ukim.finki.emt.bookcatalog.domain.models.Book;
import mk.ukim.finki.emt.bookcatalog.domain.models.BookId;
import mk.ukim.finki.emt.bookcatalog.domain.models.Publisher;
import mk.ukim.finki.emt.bookcatalog.domain.valueObjects.Category;
import mk.ukim.finki.emt.bookcatalog.service.BookService;
import mk.ukim.finki.emt.bookcatalog.service.form.BookForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookResource {

    private final BookService bookService;

    @GetMapping
    public List<Book> findAll() {
        return this.bookService.findAll();
    }

    @GetMapping("/{id}")
    public String findBookNameById(@PathVariable BookId id) {
        return this.bookService.findBookNameById(id);
    }

    @GetMapping("/categories")
    public List<Category> getAllBookCategories() {
        return Arrays.asList(Category.values());
    }

    @GetMapping("/publishers")
    public List<Publisher> getPublishers() {
        return DataInitializer.publishers;
    }

    @PostMapping
    public Book save(@RequestBody BookForm bookForm) {
        return this.bookService.createBook(bookForm);
    }

    @GetMapping("/change-currency")
    public void changeCurrency(@RequestParam String currency) {
        this.bookService.changeCurrency(Currency.valueOf(currency));
    }

}
