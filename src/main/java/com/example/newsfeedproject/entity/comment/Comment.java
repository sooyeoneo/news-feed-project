package com.example.newsfeedproject.entity.comment;

import com.example.newsfeedproject.entity.BaseEntity;
import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private int countLike = 0;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {}

    public Comment(String comment) {
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public void plusLike(){
        this.countLike++;
    }

    public void minusLike(){
        this.countLike--;
    }
}
