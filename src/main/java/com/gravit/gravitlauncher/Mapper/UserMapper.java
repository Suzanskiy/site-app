package com.gravit.gravitlauncher.Mapper;

import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.mapstruct.Mapper;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToDTO (UserEntity userEntity);

    UserEntity userToEntity (UserDTO userDTO);

    UserDetails userEntityToUserDetails (UserEntity userEntity);

//    List<UserDTO> userListToDTO (List<UserEntity> userEntityList);
//
//    List<UserEntity> userListToEntiti(List<UserDTO> userDTOList);
}
