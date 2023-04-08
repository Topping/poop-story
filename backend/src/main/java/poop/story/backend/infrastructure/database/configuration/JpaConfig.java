package poop.story.backend.infrastructure.database.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import poop.story.backend.infrastructure.database.Auditor;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = {"poop.story.backend.domain.repository"})
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new Auditor();
    }
}
