package com.api.BooksApiWithMySql.repository.one_one;

import com.api.BooksApiWithMySql.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {
}
