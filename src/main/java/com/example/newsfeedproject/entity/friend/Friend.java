package com.example.newsfeedproject.entity.friend;

import com.example.newsfeedproject.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.BitSet;

@Entity
@Table(name = "friends")
@Getter
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Long fromUserId;

    @Setter
    @Column(nullable = false)
    private Long toUserId;

    @Setter
    @Column(nullable = false)
    private Byte areWeFriend;

    @Setter
    @OneToMany
    @JoinColumn(name = "user_id")
    private User user;

    public Friend(Long fromUserId, Long toUserId, Byte areWeFriend) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.areWeFriend= areWeFriend;
    }
}
