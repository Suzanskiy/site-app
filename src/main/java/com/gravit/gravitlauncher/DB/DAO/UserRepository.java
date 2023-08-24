package com.gravit.gravitlauncher.DB.DAO;

import com.gravit.gravitlauncher.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserEntity, Long> {
    Optional<UserEntity> findByUserNameIgnoreCase (String username);

    Optional<UserEntity> existsUserEntityByUserNameIgnoreCase(String username);

}
