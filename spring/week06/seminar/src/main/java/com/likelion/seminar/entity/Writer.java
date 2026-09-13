package com.likelion.seminar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Writer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    public Writer(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "writer")
    @ToString.Exclude
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
        post.setWriter(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setWriter(this);
    }
}