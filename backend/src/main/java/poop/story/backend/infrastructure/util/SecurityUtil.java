package poop.story.backend.infrastructure.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

public class SecurityUtil {
    private SecurityUtil() {}
    public static Optional<String> getAuthSubject() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName);
    }
}
