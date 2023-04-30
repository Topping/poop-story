package poop.story.backend.infrastructure.database;

import org.springframework.data.domain.AuditorAware;
import poop.story.backend.infrastructure.util.SecurityUtil;

import java.util.Optional;

public class Auditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtil.getAuthSubjectOptional();
    }
}
