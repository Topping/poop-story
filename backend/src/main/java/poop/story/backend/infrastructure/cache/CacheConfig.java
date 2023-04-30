package poop.story.backend.infrastructure.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    public static final String COUNTRY_CACHE = "countries";
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(COUNTRY_CACHE);
    }
}
