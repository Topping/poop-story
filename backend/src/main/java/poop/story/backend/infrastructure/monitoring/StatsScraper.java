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
    private static final long FIVE_MINUTES_MS = 5 * 60 * 1000;

    private final VisitAggregationRepository aggregationRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public StatsScraper(VisitAggregationRepository aggregationRepository, CountryRepository countryRepository) {
        this.aggregationRepository = aggregationRepository;
        this.countryRepository = countryRepository;
    }

    @Scheduled(fixedDelay = FIVE_MINUTES_MS, initialDelay = FIVE_MINUTES_MS)
    @SchedulerLock(lockAtLeastFor = "PT5M", lockAtMostFor = "PT10M")
    public void scrape() {
        aggregationRepository.countVisitByCountry().forEach(this::registerCount);
    }

    private void registerCount(VisitCountryAggregation aggregation) {
        var country = countryRepository.findCountryByCommonName(aggregation.country());
        var center = country.boundary().getCentroid();
        var lon = center.getX();
        var lat = center.getY();
        var tags = List.of(Tag.of("longitude", String.valueOf(lon)), Tag.of("latitude", String.valueOf(lat)));
        Metrics.gauge(VISIT_COUNT_KEY, tags, aggregation.count());
    }
}
