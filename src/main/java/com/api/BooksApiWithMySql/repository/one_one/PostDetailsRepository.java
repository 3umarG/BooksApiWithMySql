package com.api.BooksApiWithMySql.repository.one_one;

import com.api.BooksApiWithMySql.models.PostDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDetailsRepository extends JpaRepository<PostDetails, Integer> {
}
