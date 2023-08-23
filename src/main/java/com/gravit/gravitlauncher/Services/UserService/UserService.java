package com.gravit.gravitlauncher.Services.UserService;

import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;

public interface UserService {

    UserEntity registerUser(UserDTO UserDTO);
    UserDTO findByUserName(String userName);

    Boolean loginUser(String userName, String rawPassword);

    Boolean isUserNameAvailable (String userName);
}
