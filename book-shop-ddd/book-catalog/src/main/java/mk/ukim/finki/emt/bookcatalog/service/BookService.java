package mk.ukim.finki.emt.bookcatalog.service;

import mk.ukim.finki.emt.bookcatalog.domain.models.Book;
import mk.ukim.finki.emt.bookcatalog.domain.models.BookId;
import mk.ukim.finki.emt.bookcatalog.service.form.BookForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;

import java.util.List;

public interface BookService {

    /**
     * This method returns the book with the given id
     * @param id
     * @return
     */
    Book findById(BookId id);

    /**
     * This method returns the book name of the book with the given
     * @param id
     * @return
     */
    String findBookNameById(BookId id);

    /**
     * This method creates a new book with data from the form
     * @param form
     * @return
     */
    Book createBook(BookForm form);

    /**
     * This method returns the book with id bookId but with new sales number (increased by quantity)
     * @param bookId
     * @param quantity
     * @return
     */
    Book orderItemCreated(BookId bookId, int quantity);

    /**
     * This method returns the book with id bookId but with new sales number (decreased by quantity)
     * @param bookId
     * @param quantity
     * @return
     */
    Book orderItemRemoved(BookId bookId, int quantity);

    /**
     * This method returns all books
     * @return
     */
    List<Book> findAll();

    /**
     * This method changes the currency of all books
     * @param currency
     */
    void changeCurrency(Currency currency);

}
