package com.example.my.domain.auth.service;

import com.example.my.common.dto.ResDTO;
import com.example.my.common.exception.BadRequestException;
import com.example.my.domain.auth.dto.req.ReqJoinDTOApiV1;
import com.example.my.model.user.constraint.RoleType;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.entity.UserRoleEntity;
import com.example.my.model.user.repository.UserRepository;
import com.example.my.model.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceApiV1 {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

// 시큐리티 없을 때의 코드
//    public ResponseEntity<?> login(ReqLoginDTOApiV1 dto, HttpSession session){
//        Optional<UserEntity> userEntityOptional = userRepository.findByIdAndDeleteDateIsNull(dto.getUser().getId());
//        if (userEntityOptional.isEmpty()) {
//            throw new BadRequestException("존재하지 않는 사용자입니다.");
//        }
//        UserEntity userEntity = userEntityOptional.get();
//        if (!userEntity.getPassword().equals(dto.getUser().getPassword())) {
//            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
//        }
//        session.setAttribute("loginUserDTO", LoginUserDTO.of(userEntity));
//        return new ResponseEntity<>(
//                ResponseDTO.builder()
//                        .code(0)
//                        .message("로그인에 성공하였습니다.")
//                        .build(),
//                HttpStatus.OK
//        );
//    }

    @Transactional
    public ResponseEntity<ResDTO<Object>> join(ReqJoinDTOApiV1 dto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(dto.getUser().getUsername());
        if (userEntityOptional.isPresent()) {
            throw new BadRequestException("이미 존재하는 아이디입니다.");
        }
        UserEntity userEntityForSaving = UserEntity.builder()
                .username(dto.getUser().getUsername())
//                .password(dto.getUser().getPassword()) // 시큐리티 없을 때의 코드
                .password(passwordEncoder.encode(dto.getUser().getPassword()))
                .createDate(LocalDateTime.now())
                .build();
        UserEntity userEntity = userRepository.save(userEntityForSaving);
        UserRoleEntity userRoleEntityForSaving = UserRoleEntity.builder()
                .userEntity(userEntity)
                .role(RoleType.USER)
                .build();
        userRoleRepository.save(userRoleEntityForSaving);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("회원가입에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}
