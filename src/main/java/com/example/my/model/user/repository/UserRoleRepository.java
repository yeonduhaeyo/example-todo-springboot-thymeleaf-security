package com.example.my.model.user.repository;

import com.example.my.model.user.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findByUserEntity_Id(Long id);

    UserRoleEntity findByUserEntity_IdAndRole(Long id, String role);

}
