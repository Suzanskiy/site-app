package com.gravit.gravitlauncher.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilter {

    private final AuthenticationProvider autenticaltionProvider;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        RequestMatcher loginMatcher = new AntPathRequestMatcher("/api/login");
        RequestMatcher regMatcher = new AntPathRequestMatcher("/api/registration");
        RequestMatcher afterLoginPage = new AntPathRequestMatcher("/api/afterLoginPage");
        RequestMatcher logoutPage = new AntPathRequestMatcher("/api/logout", "POST");
       return http
               .csrf(csrf -> csrf.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                       .csrfTokenRepository(csrfTokenRepository)
                       .sessionAuthenticationStrategy(new CsrfAuthenticationStrategy(csrfTokenRepository)))
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers(afterLoginPage).authenticated()
                       .requestMatchers(loginMatcher, regMatcher).permitAll()
                       .anyRequest().permitAll())
               .formLogin(formlogin -> formlogin
                       .usernameParameter("userName")
                       .passwordParameter("password")
                       .loginPage("/api/login")
                       .defaultSuccessUrl("/api/afterLoginPage", true))
               .logout(logout -> logout.
                       logoutRequestMatcher(logoutPage)
                       .logoutSuccessUrl("/api/login")
                       .invalidateHttpSession(true)
                       .deleteCookies("JSESSIONID"))
               .sessionManagement(session  -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
               .authenticationProvider(autenticaltionProvider)
               .build();
    }

}
