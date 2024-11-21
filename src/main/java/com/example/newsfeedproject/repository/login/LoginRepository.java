package com.example.newsfeedproject.repository.login;

import com.example.newsfeedproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "존재하지 않는 email 입니다. "
                        )
                );
    }
}
