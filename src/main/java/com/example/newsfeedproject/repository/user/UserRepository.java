package com.example.newsfeedproject.repository.user;

import com.example.newsfeedproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
