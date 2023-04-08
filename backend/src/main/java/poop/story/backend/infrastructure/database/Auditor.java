package poop.story.backend.infrastructure.database;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

public class Auditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
//        Optional.ofNullable(SecurityContextHolder.getContext())
//            .map(c -> c.getAuthentication())
//            .map(c -> c.getPrincipal())

        return Optional.of("test");
    }
}
