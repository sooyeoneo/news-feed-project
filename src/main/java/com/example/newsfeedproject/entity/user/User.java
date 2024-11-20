package com.example.newsfeedproject.entity.user;

import com.example.newsfeedproject.entity.BaseEntity;
import com.example.newsfeedproject.entity.friend.Friend;
import com.example.newsfeedproject.entity.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String age;

//    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Friend> friends = new ArrayList<>();

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

}
