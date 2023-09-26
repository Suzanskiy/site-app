package com.gravit.gravitlauncher.Services;

import com.gravit.gravitlauncher.Authentication.AuthenticationRequest;
import com.gravit.gravitlauncher.Authentication.AuthenticationResponse;
import com.gravit.gravitlauncher.Authentication.RegisterRequest;
import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Excpetion.CustomException;
import com.gravit.gravitlauncher.Mapper.UserMapper;
import com.gravit.gravitlauncher.Services.MailService.MailSTMPService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.gravit.gravitlauncher.Excpetion.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MailSTMPService mailSTMPService;

public void register(RegisterRequest request) {
    UserDTO userDTO = buildUserDTO(request);
    UserEntity userEntity = userMapper.userToEntity(userDTO);
    sendConfirmMessageToEmail(request);
    userRepository.save(userEntity);
}

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication result = performAuthentication(request.getUserName(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(result);
        UserDetails userDetails = (UserDetails) result.getPrincipal();
        return buildUserDetails(userDetails);
    }

    private Authentication performAuthentication(String userName, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManager.authenticate(authentication);
    }

    private UserDTO buildUserDTO (RegisterRequest request) {
        checkRequestForAvailabilityNull(request);
        checkUserNameAvailabilityNullAndIsEmpty(request);
        checkEmailAvailiabilityNullAndIsEmpty(request);
        checkUserNameAlreadyExists(request);
        UserDTO user = UserDTO.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleNames(List.of("USER"))
                .build();
        return user;
    }

   private AuthenticationResponse buildUserDetails (UserDetails userDetails) {
       Collection<String> roles = userDetails.getAuthorities().stream()
               .map(GrantedAuthority::getAuthority)
               .collect(Collectors.toList());
       return AuthenticationResponse.builder()
            .userName(userDetails.getUsername())
            .roles(roles)
            .build();
   }

    //------------------------CheckExcpetions------------------///
    private void sendConfirmMessageToEmail (RegisterRequest request) {
        try {
            String verificationLinks = "http://localhost:8080/api/verify-email?email=" + request.getEmail();
            mailSTMPService.sendSimpleEmail(request.getEmail(), "Please virify email " + request.getEmail(),
                    "Click on the link " + verificationLinks);
        } catch (Exception e) {
            throw new CustomException(SMPT_FAILURE);
        }
    }
    private void checkRequestForAvailabilityNull(RegisterRequest request) {
        if (request == null) {
            throw new CustomException(NULL_REQUEST);
        }
    }
    private void checkUserNameAvailabilityNullAndIsEmpty(RegisterRequest request) {
        if (request.getUserName() == null || request.getUserName().isEmpty()) {
            throw new CustomException(USER_NAME_IS_NULL_OR_EMPTY);
        }
    }

    private void checkEmailAvailiabilityNullAndIsEmpty (RegisterRequest request) {
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                throw new CustomException(EMAIL_IS_NULL_OR_EMPTY);
            }
    }
    private void checkUserNameAlreadyExists (RegisterRequest request) {
        if (userRepository.findByUserNameIgnoreCase(request.getUserName()).isPresent()) {
            throw new CustomException(USER_NAME_IS_ALREADY_EXIST, request.getUserName());
        }
    }

}
