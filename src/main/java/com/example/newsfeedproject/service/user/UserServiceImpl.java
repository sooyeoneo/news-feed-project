package com.example.newsfeedproject.service.user;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.user.SignUpResponseDto;
import com.example.newsfeedproject.dto.user.UserResponseDto;
import com.example.newsfeedproject.entity.user.DeleteUser;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.user.DeleteUserRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import com.example.newsfeedproject.validation.PasswordValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidation passwordValidation;
    private final DeleteUserRepository deleteUserRepository;

    //유저 생성
    public SignUpResponseDto signup(String username, String email, String password, String age) {

        if(!passwordValidation.isValidPassword(password)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 형식이 다릅니다.");
        }
        //같은 아이디 존재할 시 가입 불가
        if(userRepository.findUserByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 아이디가 존재합니다.");
        }
        //삭제된 이메일과 같은 이메일을 사용할 시 가입 불가
        if(deleteUserRepository.findUserByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이 이메일은 사용할 수 없습니다.");
        }

        User user = new User(username, email, passwordEncoder.encode(password), age);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getEmail(), savedUser.getAge(), savedUser.getCreateTime());
    }

    //유저 전체 조회
    public List<UserResponseDto> findAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }

    //비밀번호 변경
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        User findUser = userRepository.findUserByIdOrElseThrow(id);

        if(!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        if(!passwordValidation.isValidPassword(newPassword)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 형식이 다릅니다.");
        }

        findUser.updatePassword(passwordEncoder.encode(newPassword));
    }

    // 유저 삭제
    public void deleteUser(Long id, String password) {

        User findUser = userRepository.findUserByIdOrElseThrow(id);

        //삭제한 이메일을 DeleteUser DB에 추가
        DeleteUser deleteUser = new DeleteUser(findUser.getEmail());
        deleteUserRepository.save(deleteUser);

        userRepository.delete(findUser);
    }
}
