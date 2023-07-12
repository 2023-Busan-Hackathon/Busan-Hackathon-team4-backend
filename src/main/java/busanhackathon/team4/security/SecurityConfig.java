package busanhackathon.team4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthFailureHandler customAuthFailureHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 권한에 따라 허용하는 url 설정
        // /login, /signup 페이지는 모두 허용, 다른 페이지는 인증된 사용자만 허용
        http.authorizeRequests().antMatchers("/login", "/join").permitAll()
                .antMatchers("**").permitAll();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/정해야됨")
                .loginProcessingUrl("/login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .failureHandler(customAuthFailureHandler) //로그인 실패 핸들러
                .permitAll();

        http.logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/login") //로그아웃 후 로그인으로 이동
                .invalidateHttpSession(true)
                .permitAll();

        http.csrf().disable();

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
