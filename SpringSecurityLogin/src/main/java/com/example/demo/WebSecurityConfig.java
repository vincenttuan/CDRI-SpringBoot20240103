package com.example.demo;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeRequests()
	        	//.antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() // 允许访问这些路径
	            .anyRequest().authenticated()
	        .and()
	        .formLogin()
	            .permitAll()
	        .and()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        .and()
	        .exceptionHandling()
	            .authenticationEntryPoint(authenticationEntryPoint())
	        .and()
	        .cors();

	    return http.build();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
	    return new AuthenticationEntryPoint() {
	        @Override
	        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
	            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	            } else {
	                response.sendRedirect("/login");
	            }
	        }
	    };
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.builder()
            .username("admin")
            .password(encoder.encode("1234")) // 确保这里使用了加密
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

