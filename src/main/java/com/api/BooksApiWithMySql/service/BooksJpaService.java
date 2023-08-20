package com.api.BooksApiWithMySql.service;

import com.api.BooksApiWithMySql.exceptions.NotFoundResourceCustomException;
import com.api.BooksApiWithMySql.interfaces.BaseBooksService;
import com.api.BooksApiWithMySql.models.Book;
import com.api.BooksApiWithMySql.repository.BooksJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "book")
public class BooksJpaService extends BaseBooksService {

    @Autowired
    private BooksJpaRepository repository;

//    @Autowired
//    public BooksJpaService(BooksJpaRepository repository) {
//        this.repository = repository;
//    }


    @Cacheable(value = "booksCache")
    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        repository.save(book);
        return book;
    }

    @Override
    public List<Book> searchBook(String query) {
        return repository.findBooksByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    @Cacheable(value = "booksCache" ,key = "#id") // retrieve with that id
    @Override
    public Book getBookById(Long id) throws NotFoundResourceCustomException {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundResourceCustomException("There is no Book with that Id"));
    }

    @CachePut(value = "booksCache" ,key = "#result.id") // update cache value that has this id
    @Override
    public Book updateBook(Long id, Book book) throws NotFoundResourceCustomException {
        Book b = getBookById(id);

        // there is no exception :
        b.setTitle(book.getTitle());
        b.setAuthor(book.getAuthor());
        b.setDescription(book.getDescription());
        b.setPrice(book.getPrice());
        b.setQuantity(book.getQuantity());
        b.setId(book.getId());

        return repository.save(book);

    }

    @CacheEvict(value = "booksCache", key = "#id")  // delete the object on the cache with that id
    @Override
    public Book deleteBook(Long id) throws NotFoundResourceCustomException {
        Book book = getBookById(id);
        repository.delete(book);
        return book;
    }

    @Override
    public List<Book> findBooksWithPriceBetween(double start, double end) {
        return repository.findBooksByPriceBetweenOrderByPriceDesc(start, end);
    }

    @Override
    public Book findBooksByTitle(String title) {
        return repository.findBookByTitleLike(title);
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return repository.findBookByAuthorLikeOrderByPriceDesc(author);
    }


}
