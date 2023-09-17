package com.gravit.gravitlauncher.Configuration;

import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.Entity.UserEntity;
import com.gravit.gravitlauncher.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Bean
    public UserDetailsService userDetailsService () {
        return username -> {
            UserEntity userEntity = userRepository.findByUserNameIgnoreCase(username)
                    .orElseThrow(() -> new UsernameNotFoundException("username is not found"));
            return userMapper.userEntityToUserDetails(userEntity);
        };
    }
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
