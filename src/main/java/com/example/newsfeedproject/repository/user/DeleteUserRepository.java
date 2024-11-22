package com.example.newsfeedproject.repository.user;

import com.example.newsfeedproject.entity.user.DeleteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeleteUserRepository extends JpaRepository<DeleteUser, Long> {

    Optional<DeleteUser> findUserByEmail(String email);
}
