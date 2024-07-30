package ua.com.chatter.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ua.com.chatter.demo.auth.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // @Autowired
    // private UserService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Відключення захисту від підробки міжсайтових запитів (CSRF)
        http.csrf(AbstractHttpConfigurer::disable);

        // Налаштування використання HTTP або HTTPS для певних запитів
        http.requiresChannel(c -> c.requestMatchers("/actuator/**").requiresInsecure());

        // Конфігурування авторизації запитів
        http.authorizeHttpRequests(request -> {
            // Вказані URL дозволені для всіх (permitAll)
            request.requestMatchers(
                    "/api/v1/auth/registration/**",
                    "/api/v1/auth/login",
                    "/api/v1/auth/register",
                    "/api/v1/actuator/**",
                    "/ws/**"
            // "/register",
            // "/login",
            // "/actuator/**"
            ).permitAll();
            // Будь-які інші запити вимагають автентифікації (authenticated)
            request.anyRequest().authenticated();
        });

        // Конфігурування форми логіну
        http.formLogin(fL -> fL.loginPage("/login")
                .usernameParameter("phoneNumber").permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login-error"));
        // Конфігурування логауту
        http.logout(logOut -> logOut.logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "Idea-2e8e7cee")
                .logoutSuccessUrl("/login"));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    // }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
