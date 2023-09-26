package com.gravit.gravitlauncher.Services.UserService;

import com.gravit.gravitlauncher.DB.DAO.RoleRepository;
import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Excpetion.CustomException;
import com.gravit.gravitlauncher.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.gravit.gravitlauncher.Excpetion.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity registerUser(UserDTO userDTO) {
       checkUserNameAlreadyExists(userDTO);
        UserEntity userEntity = convertToUserEntity(userDTO);
        return userRepository.save(userEntity);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        UserEntity userEntity = validateAndGetUserEntityByUserName(userName);
        return userMapper.userToDTO(userEntity);
    }

    @Override
    public Boolean isUserNameAvailable(String userName) {
        Optional<UserEntity> userEntity = userRepository.findByUserNameIgnoreCase(userName);
        return !userEntity.isPresent();
    }

    public Boolean loginUser (String userName, String rawPassword) {
        UserDTO userDTO = findByUserName(userName);
        return passwordEncoder.matches(rawPassword, userDTO.getPassword());
    }

    @Override
    public Boolean verifyEmail(String email) {
        UserEntity userEntity = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomException(EMAIL_IS_BAD, email));
            userEntity.setIsEmailVerify(true);
            userRepository.save(userEntity);
            return true;
    }

    private UserEntity convertToUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRoles(List.of(roleRepository.findByRoleName("ROLE_USER").get()));
        return userEntity;
    }


    //-------EXCEPTIONS-------//

    private void checkUserNameAlreadyExists (UserDTO userDTO) {
        if (userRepository.findByUserNameIgnoreCase(userDTO.getUserName()).isPresent()) {
            throw new CustomException(USER_NAME_IS_ALREADY_EXIST, userDTO.getUserName());
        }
    }

    private UserEntity validateAndGetUserEntityByUserName(String userName) {
        return userRepository.findByUserNameIgnoreCase(userName)
                .orElseThrow(() -> new CustomException(USER_NAME_NOT_FOUND, userName));
    }


}
