package poop.story.backend.infrastructure.monitoring;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import poop.story.backend.domain.repository.CountryRepository;
import poop.story.backend.domain.repository.VisitAggregationRepository;
import poop.story.backend.domain.visit.VisitCountryAggregation;

import java.util.List;

@Component
public class StatsScraper {
    private static final String VISIT_COUNT_KEY = "io.poopstory.visits";
    private static final long ONE_HOUR_MS = 60L * 60L * 1000L;

    private final VisitAggregationRepository aggregationRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public StatsScraper(VisitAggregationRepository aggregationRepository, CountryRepository countryRepository) {
        this.aggregationRepository = aggregationRepository;
        this.countryRepository = countryRepository;
    }

    @Scheduled(fixedDelay = ONE_HOUR_MS, initialDelay = ONE_HOUR_MS)
    @SchedulerLock(lockAtLeastFor = "PT5M", lockAtMostFor = "PT10M")
    public void scrape() {
        aggregationRepository.countVisitByCountry().forEach(this::registerCount);
    }

    private void registerCount(VisitCountryAggregation aggregation) {
        if (aggregation.count() == 0) return;
        var country = countryRepository.findCountryByCommonName(aggregation.country());
        var center = country.boundary().getCentroid();
        var lon = center.getX();
        var lat = center.getY();
        var tags = List.of(Tag.of("longitude", String.valueOf(lon)), Tag.of("latitude", String.valueOf(lat)));
        Metrics.gauge(VISIT_COUNT_KEY, tags, aggregation.count());
    }
}
