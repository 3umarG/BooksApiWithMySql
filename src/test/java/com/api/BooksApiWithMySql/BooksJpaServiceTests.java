package com.api.BooksApiWithMySql;

import com.api.BooksApiWithMySql.exceptions.NotFoundResourceCustomException;
import com.api.BooksApiWithMySql.models.Book;
import com.api.BooksApiWithMySql.repository.BooksJpaRepository;
import com.api.BooksApiWithMySql.service.BooksJpaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class BooksJpaServiceTests {

    @MockBean
    private BooksJpaRepository repository;

    @Autowired
    private BooksJpaService service;

    @Test
    public void findBookById_sameBookIdMultipleTimes_oneTimeCall() throws NotFoundResourceCustomException {

        // Arrange
        Long bookId = 1L;
        Mockito.when(repository.findById(bookId))
                .thenReturn(Optional.of(new Book(bookId,
                        "A",
                        "Title 1",
                        "Author 1",
                        5.2,
                        2)));


        // Act
        service.getBookById(bookId);
        service.getBookById(bookId);
        service.getBookById(bookId);
        service.getBookById(bookId);
        service.getBookById(bookId);


        // Assert
        Mockito.verify(repository, Mockito.times(1)).findById(bookId);

    }
}
