package com.api.BooksApiWithMySql.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "posts_details")
@NoArgsConstructor
public class PostDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "written_by", length = 100)
    private String writtenBy;

    @Column(name = "published_at")
    private Date publishedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id" ,unique = true)
    @JsonIgnore
    private Post post;

    public PostDetails(String writtenBy, Date publishedDate) {
        this.writtenBy = writtenBy;
        this.publishedDate = publishedDate;
    }
}
