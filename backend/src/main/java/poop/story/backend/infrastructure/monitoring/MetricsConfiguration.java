package poop.story.backend.infrastructure.monitoring;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {
    @Bean
    public MeterFilter ignoreSpringSecurityFilter() {
        return MeterFilter.deny(id -> id.getName().startsWith("spring.security"));
    }
    @Bean
    public MeterFilter ignoreSpringRepositoryFiter() {
        return MeterFilter.deny(id -> id.getName().startsWith("spring.data"));
    }

    @Bean
    public MeterFilter ignoreHttpMetricsWithUnknownUriFilter() {
        return MeterFilter.deny(id ->
            id.getName().startsWith("http.server.requests")
            && (id.getTag("uri") == null || "UNKNOWN".equals(id.getTag("uri")))
        );
    }
}
