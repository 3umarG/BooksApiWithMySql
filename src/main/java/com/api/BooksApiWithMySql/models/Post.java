package com.api.BooksApiWithMySql.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50, name = "post_title")
    private String title;

    @Column(name = "post_description")
    private String description;

    @Column(name = "is_published")
    private boolean published;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "post")
    private PostDetails details;

    public Post(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
