package poop.story.backend.application.country.seed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import poop.story.backend.domain.country.Country;
import poop.story.backend.domain.repository.CountryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class GeoJsonLoader {
    private static final Logger LOG = LoggerFactory.getLogger(GeoJsonLoader.class);
    private static final String COUNTRY_DATA_FILE = "/data/countries.geojson";

    private final ObjectMapper objectMapper;
    private final CountryRepository countryRepository;

    @Autowired
    public GeoJsonLoader(ObjectMapper objectMapper, CountryRepository countryRepository) {
        this.objectMapper = objectMapper;
        this.countryRepository = countryRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seedCountryData() {
        try (var is = getClass().getResourceAsStream(COUNTRY_DATA_FILE)) {
            if (is == null) {
                LOG.info("No country geodata file present. Skipping seed.");
                return;
            }
            if (countryRepository.findFirst().isPresent()) return;

            GeoJsonFeatureCollection collection = objectMapper.readValue(is, GeoJsonFeatureCollection.class);
            collection.features.stream()
                .filter(this::hasValidFields)
                .map(this::buildCountry)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(countryRepository::save);
            countryRepository.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasValidFields(GeoJsonFeature feature) {
        return StringUtils.isNotEmpty(feature.properties().commonName())
            && StringUtils.isNotEmpty(feature.properties().iso2Code())
            && StringUtils.isNotEmpty(feature.properties().iso3Code());
    }

    private Optional<Country> buildCountry(GeoJsonFeature feature) {
        return buildMultiPolygon(feature.geometry())
            .map(multiPolygon ->
                new Country(
                    feature.properties().commonName(),
                    feature.properties().iso3Code(), feature.properties().iso2Code(),
                    multiPolygon
                )
            );
    }

    private Optional<MultiPolygon> buildMultiPolygon(Boundary boundary) {
        var fac = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        var reader = new GeoJsonReader(fac);
        try {
            var jsonString = objectMapper.writeValueAsString(boundary);
            var polygon = (MultiPolygon) reader.read(jsonString);
            return Optional.of(polygon);
        } catch (JsonProcessingException | ParseException e) {
            LOG.error("Failed serializing boundary.");
            return Optional.empty();
        }
    }

    private record GeoJsonFeatureCollection(@JsonProperty("features") List<GeoJsonFeature> features) {}
    private record GeoJsonFeature(@JsonProperty("properties") CountryProperties properties, @JsonProperty("geometry") Boundary geometry) {}
    private record CountryProperties(@JsonProperty("ADMIN") String commonName, @JsonProperty("ISO_A3") String iso3Code, @JsonProperty("ISO_A2") String iso2Code) {}
    private record Boundary(@JsonProperty("type") String type, @JsonProperty("coordinates") List<List<List<List<Double>>>> coordinates) {}
}
