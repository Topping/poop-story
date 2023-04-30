package poop.story.backend.domain.repository;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poop.story.backend.domain.visit.Visit;

import java.util.List;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {
    List<Visit> findAllByCreatorId(String creatorId);
    Visit findByIdAndCreatorId(UUID id, String creatorId);
    @Query(value = "SELECT * FROM poopstory.visits WHERE creator_id = ?1 AND ST_Covers(?2, visits.location)", nativeQuery = true)
    List<Visit> findVisitsWithinBoundingBox(String creatorId, Geometry geometry);

    @Query(value = "SELECT poopstory.visits.* FROM poopstory.visits " +
        "INNER JOIN poopstory.countries " +
        "ON (poopstory.countries.iso2_code = :iso2Code AND poopstory.visits.country_id = poopstory.countries.id)" +
        "WHERE poopstory.visits.creator_id = :creatorId", nativeQuery = true)
    List<Visit> findVisitsWithinCountry(@Param("creatorId") String creatorId, @Param("iso2Code") String iso2Code);
}
