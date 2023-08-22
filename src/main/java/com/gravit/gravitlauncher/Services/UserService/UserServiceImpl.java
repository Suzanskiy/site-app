package com.gravit.gravitlauncher.Services.UserService;

import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity registerUser(UserDTO userDTO) {
        if (userRepository.findByUserNameIgnoreCase(userDTO.getUserName()).isPresent()) {
            throw new RuntimeException("Username - " + userDTO.getUserName() + " already exists!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        return userRepository.save(userEntity);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserNameIgnoreCase(userName).
                orElseThrow(() -> new RuntimeException("User - " + userName + " not found"));
        return userMapper.userToDTO(userEntity);
    }

    public Boolean loginUser (String userName, String rawPassword) {
        UserDTO userDTO = findByUserName(userName);
        if (userDTO == null) {
            return false;
        }
        return userDTO.getPassword().equals(rawPassword);
    }
}
