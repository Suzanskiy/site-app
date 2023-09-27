package com.gravit.gravitlauncher.Mapper;

import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserMapper {

    UserDTO userToDTO (UserEntity userEntity);

    UserEntity userToEntity (UserDTO userDTO);

    UserDetails userEntityToUserDetails (UserEntity userEntity);

}
