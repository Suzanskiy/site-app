package com.gravit.gravitlauncher.Services.EmailVerificationService;

import com.gravit.gravitlauncher.DB.DAO.UserRepository;
import com.gravit.gravitlauncher.Entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final UserRepository userRepository;
    @Override
    public void verifiEmail(String email, HttpServletResponse response) throws IOException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailIgnoreCase(email);
        if (optionalUserEntity.isEmpty()) {
            redirectWithError(response, "Invalid email" );
            return;
        }
        UserEntity userEntity = optionalUserEntity.get();
        if(userEntity.getIsEmailVerify()) {
            redirectWithError(response, "Email already verified");
            return;
        }

        userEntity.setIsEmailVerify(true);
        userRepository.save(userEntity);
        response.sendRedirect("/api/login?emailVerified=true");
    }

    private void redirectWithError (HttpServletResponse response, String errorMessage) throws IOException{
        response.sendRedirect("/api/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8));
    }
}
