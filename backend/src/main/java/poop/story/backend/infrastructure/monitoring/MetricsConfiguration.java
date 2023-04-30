package poop.story.backend.infrastructure.monitoring;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {
    @Bean
    public MeterFilter ignoreSpringSecurityFilter() {
        return MeterFilter.deny(id -> id.getName().contains("spring.security"));
    }
}
