package com.example.demo;

import java.io.IOException;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity // 啟用Spring Security
@Configuration // 表示這是一個配置類
public class WebSecurityConfig {
	
	/**
	 * 以下是Spring Security預設提供的一些重要安全頭部：

	    X-Content-Type-Options:
	        預設值為 nosniff。
	        此頭部指示瀏覽器遵守服務器聲明的內容類型，防止MIME類型嗅探攻擊。
	
	    X-Frame-Options:
	        預設值通常為 DENY 或 SAMEORIGIN。
	        此頭部用於防止點擊劫持攻擊，限制網頁被嵌入到 <frame>、<iframe> 或 <object> 中。
	
	    Strict-Transport-Security (HSTS):
	        當使用HTTPS時預設啟用。
	        HSTS頭部強制客戶端（如瀏覽器）僅通過HTTPS與服務器通信，提高數據傳輸的安全性。
	
	    Cache-Control 和 Pragma:
	        用於禁止瀏覽器緩存敏感數據。
	        這些頭部有助於防止在使用共用計算機後，通過瀏覽器的後退按鈕洩露用戶數據。
	
	    X-XSS-Protection:
	        在某些舊版本的Spring Security中預設啟用，但在較新的版本中已被棄用，因為現代瀏覽器提供了更好的XSS保護機制。
	
	    Content-Security-Policy (CSP):
	        在某些版本的Spring Security中，可以選擇啟用CSP。
	        CSP是一種強大的安全機制，可以幫助減輕跨站腳本攻擊（XSS）的風險。
	
	    Referrer-Policy:
	        可以用來控制在請求時發送哪些網頁來源信息。
	
	    Feature-Policy:
	        可以用來指定哪些瀏覽器特性和API在當前網頁中可以使用。
	 * */
    @Bean // 定義一個Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 禁用CSRF（跨站請求偽造）保護
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll() // 允許對/login和靜態資源（如CSS、JS、圖片）的訪問，無需認證
                .requestMatchers("/manager").hasRole("ADMIN") // 只有ADMIN角色可以访问/manager
                .requestMatchers("/", "/hello").hasAnyRole("USER", "ADMIN") // USER和ADMIN角色可以访问/和/hello
                .anyRequest().authenticated()) // 要求所有其他請求都必須經過認證
            .formLogin(form -> form
                .permitAll()) // 允許所有用戶訪問表單登錄頁面
            .logout(logout -> logout
            	.logoutSuccessUrl("/login") // 登出成功後跳轉到 /login
            	.invalidateHttpSession(true) // 登出時使 HttpSession 失效
            	.deleteCookies("JSESSIONID") // 删除 JSESSIONID cookie
             )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 根據需要創建會話（Session）
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(authenticationEntryPoint())) // 設置自定義的身份驗證入口點
            .cors(Customizer.withDefaults()); // 啟用CORS（跨域資源共享），使用預設配置

        return http.build(); // 建立安全過濾器鏈
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
            .username("user")
            .password(encoder.encode("1234"))
            .roles("USER") // 將自動添加前娺字 "ROLE_"
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(encoder.encode("1234"))
            .roles("ADMIN") // 將自動添加前娺字 "ROLE_"
            .build();

        return new InMemoryUserDetailsManager(user, admin);
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

