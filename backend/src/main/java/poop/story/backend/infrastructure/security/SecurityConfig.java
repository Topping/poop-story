package poop.story.backend.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.jwks-uri}")
    private String jwksUri;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        http
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers(HttpMethod.GET).authenticated()
            .pathMatchers(HttpMethod.POST).authenticated()
            .pathMatchers(HttpMethod.PUT).authenticated()
            .pathMatchers(HttpMethod.DELETE).authenticated()
            .pathMatchers(HttpMethod.HEAD).authenticated()
            .pathMatchers(HttpMethod.PATCH).authenticated()
            .pathMatchers(HttpMethod.TRACE).authenticated()
            .and()
            .cors().and()
            .oauth2ResourceServer().jwt()
            .jwtDecoder(jwtDecoder()) ;
//            .authenticationManager(new AuthenticationManager());
        return http.build();
    }

    ReactiveJwtDecoder jwtDecoder() {
        /*
        By default, Spring Security does not validate the "aud" claim of the token, to ensure that this token is
        indeed intended for our app. Adding our own validator is easy to do:
        */

        var jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwksUri).build();
//        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
