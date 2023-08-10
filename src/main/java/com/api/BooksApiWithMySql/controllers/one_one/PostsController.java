package com.api.BooksApiWithMySql.controllers.one_one;

import com.api.BooksApiWithMySql.exceptions.NotFoundBookCustomException;
import com.api.BooksApiWithMySql.models.Post;
import com.api.BooksApiWithMySql.models.PostDetails;
import com.api.BooksApiWithMySql.service.one_one.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }


    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postsService.getAllPosts());
    }

    @GetMapping("{id}")
    public ResponseEntity getPostById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(postsService.getPostById(id));
        } catch (NotFoundBookCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("{id}/details")
    public ResponseEntity getPostDetailsById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(postsService.getPostDetailsById(id));
        } catch (NotFoundBookCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity addPostWithDetails(@RequestBody Post post) {
        try {
            return ResponseEntity.ok(postsService.addPostWithDetails(post));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("{id}")
    public ResponseEntity postDetailsForPost(@PathVariable Integer id, @RequestBody PostDetails details) {
        try {
            return ResponseEntity.ok(postsService.addDetailsToPostWithId(id, details));
        } catch (NotFoundBookCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SQLIntegrityConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity deletePostById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(postsService.deletePostById(id));
        } catch (NotFoundBookCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("{id}")
    public ResponseEntity updateById(@PathVariable Integer id, @RequestBody Post updatedPost) {
        try {
            return ResponseEntity.ok(postsService.updatePostById(id, updatedPost));
        } catch (NotFoundBookCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
