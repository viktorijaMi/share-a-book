package mk.ukim.finki.emt.bookcatalog.service;

import mk.ukim.finki.emt.bookcatalog.domain.models.Book;
import mk.ukim.finki.emt.bookcatalog.domain.models.BookId;
import mk.ukim.finki.emt.bookcatalog.service.form.BookForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;

import java.util.List;

public interface BookService {

    Book findById(BookId id);
    String findBookNameById(BookId id);
    Book createBook(BookForm form);
    Book orderItemCreated(BookId bookId, int quantity);
    Book orderItemRemoved(BookId bookId, int quantity);
    List<Book> findAll();
    void changeCurrency(Currency currency);

}
