package poop.story.backend.infrastructure.util;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

import java.security.Principal;

public class SecurityUtil {
    public static Mono<String> getAuthSubject() {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName);
    }
}
