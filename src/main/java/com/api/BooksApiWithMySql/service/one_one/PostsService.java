package com.api.BooksApiWithMySql.service.one_one;

import com.api.BooksApiWithMySql.exceptions.NotFoundBookCustomException;
import com.api.BooksApiWithMySql.models.Post;
import com.api.BooksApiWithMySql.models.PostDetails;
import com.api.BooksApiWithMySql.repository.one_one.PostDetailsRepository;
import com.api.BooksApiWithMySql.repository.one_one.PostsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final PostDetailsRepository detailsRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository, PostDetailsRepository detailsRepository) {
        this.postsRepository = postsRepository;
        this.detailsRepository = detailsRepository;
    }

    public Post addPostWithDetails(Post post, PostDetails details) {
        post.setDetails(details);
        details.setPost(post);

        return postsRepository.save(post);
    }

    public Post addPostWithDetails(Post post) {
        return postsRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }

    public Post getPostById(Integer id) throws NotFoundBookCustomException {
        return postsRepository.findById(id).orElseThrow(() -> new NotFoundBookCustomException("There is no Post with that ID"));
    }

    public PostDetails getPostDetailsById(Integer id) throws NotFoundBookCustomException {
        Post p = postsRepository.findById(id).orElseThrow(() -> new NotFoundBookCustomException("There is no Post with that ID"));

        return p.getDetails();
    }

    public Post addDetailsToPostWithId(Integer id, PostDetails details) throws NotFoundBookCustomException, SQLIntegrityConstraintViolationException {
        Post post = postsRepository.findById(id).
                orElseThrow(() -> new NotFoundBookCustomException("There is no Post with that ID"));

        if (post.getDetails() != null) {
            throw new SQLIntegrityConstraintViolationException("There is already Details with that Post");
        }
        details.setPost(post);
        post.setDetails(details);
        detailsRepository.save(details);
        return postsRepository.save(post);
    }


    public Post deletePostById(Integer id) throws NotFoundBookCustomException {
        Post post = getPostById(id);
        postsRepository.delete(post);
        return post;
    }

    public Post updatePostById(Integer id, Post updatedPost) throws NotFoundBookCustomException {
        Post post = getPostById(id);
        post.setDescription(updatedPost.getDescription());
        post.setTitle(updatedPost.getTitle());
        post.setPublished(updatedPost.isPublished());

        return postsRepository.save(post);
    }
}
