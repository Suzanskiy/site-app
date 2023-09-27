package com.gravit.gravitlauncher.Mapper;

import com.gravit.gravitlauncher.DB.DAO.RoleRepository;
import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.Role;
import com.gravit.gravitlauncher.Entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final RoleRepository roleRepository;
    @Override
    public UserDTO userToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setEmailVerify(userEntity.getIsEmailVerify());
        return userDTO;
    }

    @Override
    public UserEntity userToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setRoles(convertRolesFromDTO(userDTO));
        return userEntity;
    }

    @Override
    public UserDetails userEntityToUserDetails(UserEntity userEntity) {
        Collection<SimpleGrantedAuthority> roles = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
        return new User(
                userEntity.getUserName(),
                userEntity.getPassword(),
                roles);
    }

    private List<Role> convertRolesFromDTO (UserDTO userDTO) {
        if (userDTO.getRoleNames().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return userDTO.getRoleNames().stream()
                .map(roleName -> roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found - " + roleName)))
                .collect(Collectors.toList());
    }
}
