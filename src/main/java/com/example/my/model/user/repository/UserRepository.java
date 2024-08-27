package com.example.my.model.user.repository;

import com.example.my.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndDeleteDateIsNull(String username);

    Optional<UserEntity> findByIdAndDeleteDateIsNull(Long id);
}
