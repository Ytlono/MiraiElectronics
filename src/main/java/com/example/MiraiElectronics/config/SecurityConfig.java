//package com.example.MiraiElectronics.config;
//
//import com.example.MiraiElectronics.repository.User;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/","/category/*","/auth/**", "/css/**", "/js/**", "/images/**").permitAll().anyRequest().authenticated())
//                .formLogin(login -> login
//                        .loginPage("/auth/login")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .loginProcessingUrl("/auth/process-login"))
//                        .su(authenticationSuccessHandler())
//
//
//
//        return http.build();
//    }
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return (request, response, authentication) -> {
//            String username = authentication.getName();
//            User user = userService.findByUsername(username);
//            sessionService.saveUserToSession(request, user);
//            response.sendRedirect("/home");
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    private final UserService userService;
//    private final SessionService sessionService;
//
//    public SecurityConfig(UserService userService, SessionService sessionService) {
//        this.userService = userService;
//        this.sessionService = sessionService;
//    }
//}
