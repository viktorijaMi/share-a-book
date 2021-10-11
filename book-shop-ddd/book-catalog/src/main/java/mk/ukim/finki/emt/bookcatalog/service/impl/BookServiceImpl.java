package mk.ukim.finki.emt.bookcatalog.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.bookcatalog.config.DataInitializer;
import mk.ukim.finki.emt.bookcatalog.domain.exceptions.BookNotFoundException;
import mk.ukim.finki.emt.bookcatalog.domain.models.Book;
import mk.ukim.finki.emt.bookcatalog.domain.models.BookId;
import mk.ukim.finki.emt.bookcatalog.domain.models.Publisher;
import mk.ukim.finki.emt.bookcatalog.domain.repository.BookRepository;
import mk.ukim.finki.emt.bookcatalog.service.BookService;
import mk.ukim.finki.emt.bookcatalog.service.form.BookForm;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book findById(BookId id) {
        return this.bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Override
    public String findBookNameById(BookId id) {
        Book book =  this.bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return book.getBookName();
    }

    @Override
    public Book createBook(BookForm form) {
        Publisher publisher = DataInitializer.publishers.stream().filter(p -> p.getName().equals(form.getPublisher())).findAny().orElseThrow();
        Book book = Book.build(form.getBookName(), Money.valueOf(Currency.MKD,form.getPrice()), 0, form.getCategory(), publisher, form.getBookImageUrl(), form.getQuantity());
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book orderItemCreated(BookId bookId, int quantity) {
        Book b = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        b.addSales(quantity);
        bookRepository.saveAndFlush(b);
        return b;
    }

    @Override
    public Book orderItemRemoved(BookId bookId, int quantity) {
        Book b = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        b.removeSales(quantity);
        bookRepository.saveAndFlush(b);
        return b;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public void changeCurrency(Currency currency) {
        List<Book> books = this.findAll();
        this.bookRepository.deleteAll(books);
        List<Book> newBooksList = new ArrayList<>();
        List<Publisher> publishers = new ArrayList<>();
        books.forEach(book -> publishers.add(book.getPublishedBy()));
        if (currency.equals(Currency.MKD)){
            books
                    .forEach(book -> {
                        if (!book.getPrice().getCurrency().equals(currency)){
                            newBooksList.add(Book.build(book.getBookName(), book.getPrice().eurToMkd(), book.getSales(), book.getCategory(), publishers.stream().filter(publisher -> publisher.getId().equals(book.getPublishedBy().getId())).findAny().get(), book.getBookImageUrl(), book.getQuantity()));
                        } else {
                            newBooksList.add(Book.build(book.getBookName(), book.getPrice(), book.getSales(), book.getCategory(), publishers.stream().filter(publisher -> publisher.getId().equals(book.getPublishedBy().getId())).findAny().get(), book.getBookImageUrl(), book.getQuantity()));
                        }
                    });
            this.bookRepository.saveAll(newBooksList);
        } else {
            books
                    .forEach(book -> {
                        if (!book.getPrice().getCurrency().equals(currency)){
                            newBooksList.add(Book.build(book.getBookName(), book.getPrice().mkdToEur(), book.getSales(), book.getCategory(), publishers.stream().filter(publisher -> publisher.getId().equals(book.getPublishedBy().getId())).findAny().get(), book.getBookImageUrl(), book.getQuantity()));
                        } else {
                            newBooksList.add(Book.build(book.getBookName(), book.getPrice(), book.getSales(), book.getCategory(), publishers.stream().filter(publisher -> publisher.getId().equals(book.getPublishedBy().getId())).findAny().get(), book.getBookImageUrl(), book.getQuantity()));
                        }
                    });
            this.bookRepository.saveAll(newBooksList);
        }
    }


}
