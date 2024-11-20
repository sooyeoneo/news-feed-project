package com.example.newsfeedproject.entity.post;

import com.example.newsfeedproject.entity.BaseEntity;
import com.example.newsfeedproject.entity.user.User;
import jakarta.persistence.*;
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
    @JoinColumn(name = "userId")
    private User user;

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Post() {

    }

}
