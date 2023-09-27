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

    @Override
    public UserDTO findByUserName(String userName) {
        UserEntity userEntity = validateAndGetUserEntityByUserName(userName);
        return userMapper.userToDTO(userEntity);
    }

    @Override
    public Boolean verifyEmail(String email) {
        UserEntity userEntity = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomException(EMAIL_IS_BAD, email));
            userEntity.setIsEmailVerify(true);
            userRepository.save(userEntity);
            return true;
    }
    //-------EXCEPTIONS-------//

    private UserEntity validateAndGetUserEntityByUserName(String userName) {
        return userRepository.findByUserNameIgnoreCase(userName)
                .orElseThrow(() -> new CustomException(USER_NAME_NOT_FOUND, userName));
    }


}
