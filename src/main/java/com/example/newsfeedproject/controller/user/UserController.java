package com.example.newsfeedproject.controller.user;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.user.DeleteUserRequestDto;
import com.example.newsfeedproject.dto.user.SignUpRequestDto;
import com.example.newsfeedproject.dto.user.SignUpResponseDto;
import com.example.newsfeedproject.dto.user.UpdatePasswordRequestDto;
import com.example.newsfeedproject.dto.user.UserResponseDto;
import com.example.newsfeedproject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto dto) {
        SignUpResponseDto signUpResponseDto =
                userService.signup(
                        dto.getUserName(),
                        dto.getEmail(),
                        dto.getPassword(),
                        dto.getAge()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserResponseDto> findAllUsers() {

        return userService.findAllUsers();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequestDto dto) {

        userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody DeleteUserRequestDto dto, HttpServletRequest servletRequest) {

        userService.deleteUser(id, dto.getPassword());
        HttpSession httpSession = servletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
