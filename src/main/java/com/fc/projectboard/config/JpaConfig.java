package com.fc.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("user"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될때 수정할것. (인증기능이 없을 경우 사용자를 특정할 값이 없다)
    };
}
