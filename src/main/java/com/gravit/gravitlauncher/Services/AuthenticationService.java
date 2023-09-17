package com.gravit.gravitlauncher.Services;

import com.gravit.gravitlauncher.Authentication.AuthenticationRequest;
import com.gravit.gravitlauncher.Authentication.AuthenticationResponse;
import com.gravit.gravitlauncher.Authentication.RegisterRequest;
import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.DB.DTO.UserDTO;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

//    public AuthenticationResponse register(RegisterRequest request) {
//        UserDTO user = UserDTO.builder()
//                .userName(request.getUserName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .roleNames(List.of("USER"))
//                .build();
//        UserEntity userEntity = userMapper.userToEntity(user);
//        userRepository.save(userEntity);
//        return AuthenticationResponse.builder()
//                .userName(user.getUserName())
//                .roles(user.getRoleNames())
//                .message(user.getUserName() + " Registration  successful")
//                .build();
//    }
public void register(RegisterRequest request) {
    UserDTO user = UserDTO.builder()
            .userName(request.getUserName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .roleNames(List.of("USER"))
            .build();
    UserEntity userEntity = userMapper.userToEntity(user);
    userRepository.save(userEntity);
}
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getUserName(),
//                            request.getPassword()
//                    )
//            );
//        } catch (UsernameNotFoundException e) {
//            throw new RuntimeException("Incorrect username or password");
//        }
//
//        var user = userRepository.findByUserNameIgnoreCase(request.getUserName())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        UserDetails userDetails = userMapper.userEntityToUserDetails(user);
//
//        Collection<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        return AuthenticationResponse.builder()
//                .userName(userDetails.getUsername())
//                .roles(roles)
//                .build();
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUserName(),
                request.getPassword()
        );

        Authentication result = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(result);

        UserDetails userDetails = (UserDetails) result.getPrincipal();
        Collection<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return AuthenticationResponse.builder()
                .userName(userDetails.getUsername())
                .roles(roles)
                .build();
    }



}
