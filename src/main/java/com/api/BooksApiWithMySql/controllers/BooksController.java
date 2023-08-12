package com.api.BooksApiWithMySql.controllers;

import com.api.BooksApiWithMySql.exceptions.NotFoundResourceCustomException;
import com.api.BooksApiWithMySql.factories.BaseFactory;
import com.api.BooksApiWithMySql.factories.FailureFactory;
import com.api.BooksApiWithMySql.factories.SuccessFactory;
import com.api.BooksApiWithMySql.interfaces.BaseBooksService;
import com.api.BooksApiWithMySql.models.Book;
import com.api.BooksApiWithMySql.responses.Response;
import com.api.BooksApiWithMySql.service.BooksJpaService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {
    private final BaseBooksService service;

    // For JPA Service :
    @Autowired
    public BooksController(BooksJpaService service) {
        this.service = service;
    }


    // For JDBC Service :
//    @Autowired
//    public BooksController(BooksJdbcService service) {
//        this.service = service;
//    }

    //    @RequestMapping(method=GET)
    @GetMapping("/")
    public ResponseEntity<Response<List<Book>>> getBooks() {
        BaseFactory<List<Book>> factory = new SuccessFactory<List<Book>>(service.getAllBooks());
        return ResponseEntity.ok(factory.createResponse());
    }


    @PostMapping("/")
    public ResponseEntity<Response<Book>> addBook(@RequestBody Book book) {
        service.addBook(book);
        SuccessFactory<Book> factory = new SuccessFactory<>(book);
        return ResponseEntity.ok(factory.createResponse());
    }

    // api/books/search?query="......"
    @GetMapping("/search")
    public ResponseEntity<Response<List<Book>>> searchBooks(@RequestParam String query) {
        SuccessFactory<List<Book>> factory = new SuccessFactory<>(service.searchBook(query));
        return ResponseEntity.ok(factory.createResponse());
    }


    @GetMapping("/{id}") // api/books/1
    public ResponseEntity<Response<Book>> getBookById(@PathVariable Long id) throws NotFoundResourceCustomException {

        Book book = service.getBookById(id);
        SuccessFactory<Book> factory = new SuccessFactory<>(book);
        return ResponseEntity.ok(factory.createResponse());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Book>> updateBookById(@PathVariable Long id, @RequestBody Book book) throws NotFoundResourceCustomException {
        Book b = service.updateBook(id, book);
        SuccessFactory<Book> factory = new SuccessFactory<>(b);
        return ResponseEntity.ok(factory.createResponse());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Book>> deleteBookById(@PathVariable Long id) throws NotFoundResourceCustomException {
        Book b = service.deleteBook(id);
        SuccessFactory<Book> factory = new SuccessFactory<>(b);
        return ResponseEntity.ok(factory.createResponse());
    }

    @GetMapping("/price")
    public ResponseEntity<Response<List<Book>>> getBooksWithPrice(@RequestParam(name = "start") double start, @RequestParam(name = "end") double end) {

        SuccessFactory<List<Book>> factory = new SuccessFactory<>(service.findBooksWithPriceBetween(start, end));
        return ResponseEntity.ok(factory.createResponse());

    }


    @GetMapping("/author/{author}")
    public ResponseEntity<Response<List<Book>>> getBooksWithAuthorName(@PathVariable String author) {


        SuccessFactory<List<Book>> factory = new SuccessFactory<>(service.findBooksByAuthor(author));
        return ResponseEntity.ok(factory.createResponse());

    }


    @GetMapping("/title/{title}")
    public ResponseEntity<Response<List<Book>>> getBooksWithTitle(@PathVariable String title) {

        SuccessFactory<List<Book>> factory = new SuccessFactory<>(service.findBooksByTitle(title));
        return ResponseEntity.ok(factory.createResponse());

    }

    /*
    Get Book Authors :
    /api/books/{id}/authors
     */
}