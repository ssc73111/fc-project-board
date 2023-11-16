package com.fc.projectboard.config;

import com.fc.projectboard.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) //of("user"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될때 수정할것. (인증기능이 없을 경우 사용자를 특정할 값이 없다)
                .map(SecurityContext::getAuthentication)// 컨텍스트에는 authentication 정보가 있고,
                .filter(Authentication::isAuthenticated) // 로그인한 상태인지 가져온다.
                .map(Authentication::getPrincipal) // 로그인정보를 꺼내온다. 보편적 principal 정보.
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    };
}
