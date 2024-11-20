package com.example.newsfeedproject.entity.post;

import com.example.newsfeedproject.entity.BaseEntity;
import com.example.newsfeedproject.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    public Post() {}

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updatePost(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
