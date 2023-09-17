package com.gravit.gravitlauncher.Services.UserService;

import com.gravit.gravitlauncher.DB.DAO.RoleRepository;
import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public UserEntity registerUser(UserDTO userDTO) {
        if (userRepository.findByUserNameIgnoreCase(userDTO.getUserName()).isPresent()) {
            throw new RuntimeException("Username - " + userDTO.getUserName() + " already exists!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setRoles(List.of(roleRepository.findByRoleName("ROLE_USER").get()));
        return userRepository.save(userEntity);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserNameIgnoreCase(userName).
                orElseThrow(() -> new RuntimeException("User - " + userName + " not found"));
        return userMapper.userToDTO(userEntity);
    }

    @Override
    public Boolean isUserNameAvailable(String userName) {
        Optional<UserEntity> userEntity = userRepository.findByUserNameIgnoreCase(userName);
        return !userEntity.isPresent();
    }

    public Boolean loginUser (String userName, String rawPassword) {
        UserDTO userDTO = findByUserName(userName);
        if (userDTO == null) {
            return false;
        }
        return userDTO.getPassword().equals(rawPassword);
    }
}
