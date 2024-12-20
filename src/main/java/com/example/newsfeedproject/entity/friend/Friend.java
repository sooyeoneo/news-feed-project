package com.example.newsfeedproject.entity.friend;

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

@Entity
@Table(name = "friends")
@Getter
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(nullable = false)
    private boolean areWeFriend;

    // 친구 요청을 보낸 사용자와 받은 사용자를 설정하는 생성자
    public Friend(User fromUser, User toUser, boolean areWeFriend) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.areWeFriend = areWeFriend;

    }

    // 친구 상태 업데이트 메서드
    public void setAreWeFriend(boolean areWeFriend) {
        this.areWeFriend = areWeFriend;
    }
  
    public Friend(){};
}
