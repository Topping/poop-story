package poop.story.backend.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poop.story.backend.application.model.country.CountryDTO;
import poop.story.backend.domain.repository.CountryRepository;
import poop.story.backend.infrastructure.cache.CacheConfig;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/public/country")
@Validated
public class CountryController {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    @Cacheable(CacheConfig.COUNTRY_CACHE)
    public List<CountryDTO> getAvailableCountries() {
        return countryRepository.findAll().stream()
            .map(c -> new CountryDTO(c.commonName(), c.iso2Code(), c.iso3Code()))
            .sorted(Comparator.comparing(CountryDTO::commonName))
            .toList();
    }
}
