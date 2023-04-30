package poop.story.backend.application.service.visit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poop.story.backend.application.exception.PersistFailedException;
import poop.story.backend.application.model.GeoJsonPolygon;
import poop.story.backend.application.model.Location;
import poop.story.backend.application.model.visit.VisitDTO;
import poop.story.backend.domain.repository.CountryRepository;
import poop.story.backend.domain.repository.VisitRepository;
import poop.story.backend.domain.visit.Visit;
import poop.story.backend.infrastructure.util.SecurityUtil;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class VisitService {
    private static final Logger LOG = LoggerFactory.getLogger(VisitService.class);
    private final VisitRepository repository;
    private final CountryRepository countryRepository;
    private final ObjectMapper objectMapper;
    private final GeoJsonReader geoJsonReader;

    @Autowired
    public VisitService(VisitRepository repository, CountryRepository countryRepository, ObjectMapper objectMapper, GeoJsonReader geoJsonReader) {
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.objectMapper = objectMapper;
        this.geoJsonReader = geoJsonReader;
    }

    public List<VisitDTO> fetchAllVisits(String iso2Code) {
        var creatorId = SecurityUtil.getAuthSubject();
        var visits = StringUtils.isEmpty(iso2Code)
            ? repository.findAllByCreatorId(creatorId)
            : repository.findVisitsWithinCountry(creatorId, iso2Code);

        return visits.stream()
            .map(this::mapEntityToDto)
            .toList();
    }

    @Transactional(rollbackFor = {PersistFailedException.class})
    public VisitDTO saveVisit(VisitDTO dto) {
        var creatorId = SecurityUtil.getAuthSubject();
        var targetCountry = countryRepository.findCountryContainingPoint(Location.toPoint(dto.location()));
        if (targetCountry == null) throw new PersistFailedException("Visit is not in recognized country.");

        var entity = mapDtoToEntity(targetCountry.id(), creatorId, dto);
        try {
            var savedVisit = repository.saveAndFlush(entity);
            return mapEntityToDto(savedVisit);
        } catch (Exception ex) {
            LOG.error("Failed writing new visit.", ex);
            throw new PersistFailedException("Failed while trying to save visit.");
        }
    }

    public List<VisitDTO> findWithinPolygon(GeoJsonPolygon polygon) {
        try {
            var geoJson = objectMapper.writeValueAsString(polygon);
            var geometry = geoJsonReader.read(geoJson);
            assert geometry.getGeometryType().equals(Geometry.TYPENAME_POLYGON);
            return repository.findVisitsWithinBoundingBox(SecurityUtil.getAuthSubject(), geometry).stream()
                .map(this::mapEntityToDto)
                .toList();
        } catch (ParseException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Visit mapDtoToEntity(UUID countryId, String creatorId, VisitDTO dto) {
        var location = Location.toPoint(dto.location());
        var visitTime = dto.when() == null ? Instant.now() : dto.when();
        return new Visit(countryId, creatorId, location, visitTime, dto.type());
    }

    private VisitDTO mapEntityToDto(Visit entity) {
        var location = Location.fromPoint(entity.location());
        return new VisitDTO(entity.id(), entity.visitType(), entity.visitTime(), location, entity.rating());
    }
}
