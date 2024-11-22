package com.example.newsfeedproject.entity.post;

import com.example.newsfeedproject.entity.BaseEntity;
import com.example.newsfeedproject.entity.comment.Comment;
import com.example.newsfeedproject.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String contents;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private int countLike = 0;

    public Post() {}

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updatePost(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void plusLike(){
        this.countLike++;
    }

    public void minusLike(){
        this.countLike--;
    }
}
