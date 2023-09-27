package com.gravit.gravitlauncher.Services.UserService;

import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;

public interface UserService {

    UserDTO findByUserName(String userName);

    Boolean verifyEmail (String email);

}
