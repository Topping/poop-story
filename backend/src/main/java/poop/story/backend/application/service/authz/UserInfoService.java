package poop.story.backend.application.service.authz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class UserInfoService {
    private final WebClient webClient;

    public UserInfoService(@Value("${auth0.domain}") String userInfoDomain) {
        this.webClient = WebClient.builder()
            .baseUrl(userInfoDomain)
            .build();
    }

    public Optional<UserInfo> getUserInfo() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(JwtAuthenticationToken.class::isInstance)
            .map(a -> (JwtAuthenticationToken)a)
            .map(AbstractOAuth2TokenAuthenticationToken::getToken)
            .map(AbstractOAuth2Token::getTokenValue)
            .flatMap(token -> webClient.get()
                .uri(builder -> builder.pathSegment("userinfo").build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .exchangeToMono(res -> res.statusCode().is2xxSuccessful()
                    ? res.bodyToMono(Auth0UserInfo.class)
                    : res.createError())
                .blockOptional()
            )
            .map(u -> new UserInfo(u.sub(), u.email()));
    }
}
