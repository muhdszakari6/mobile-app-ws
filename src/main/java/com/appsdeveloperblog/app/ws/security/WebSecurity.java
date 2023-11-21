package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableMethodSecurity(securedEnabled=true, prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class WebSecurity{
//    Class that will be used to look up user details in the database
    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository){
        this.userDetailsService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    protected AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/authorize");
        return filter;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        Configure Authentication Manager Builder
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/authorize")
                .permitAll()
                .requestMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_URL)
                .permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
                .permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
                .permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.H2_CONSOLE)
                .permitAll()
                .requestMatchers( "/v2/api-docs","/configuration/**","/swagger*/**", "/webjars/**")
                .permitAll()
                .requestMatchers( "/ping")
                .permitAll()
//                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .addFilter(getAuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager,userRepository))
                .authenticationManager(authenticationManager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();


        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setMaxAge(Long.valueOf(3600));
        configuration.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
