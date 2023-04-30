package poop.story.backend.infrastructure.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import poop.story.backend.application.exception.MissingSubjectException;

import java.security.Principal;
import java.util.Optional;

public class SecurityUtil {
    private SecurityUtil() {}
    public static String getAuthSubject() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName)
            .orElseThrow(MissingSubjectException::new);
    }

    public static Optional<String> getAuthSubjectOptional() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName);
    }
}
