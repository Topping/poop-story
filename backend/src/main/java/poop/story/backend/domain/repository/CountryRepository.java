package poop.story.backend.domain.repository;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import poop.story.backend.domain.country.Country;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    @Query(value = "SELECT * FROM poopstory.countries WHERE ST_Covers(countries.boundary, ?1);", nativeQuery = true)
    Country findCountryContainingPoint(Point point);

    @Query(value = "SELECT * FROM poopstory.countries LIMIT 1;", nativeQuery = true)
    Optional<Country> findFirst();

    @Query(value = "SELECT poopstory.countries.common_name FROM poopstory.countries " +
        " INNER JOIN poopstory.visits " +
        " ON (poopstory.visits.creator_id = :creatorId AND poopstory.countries.id = poopstory.visits.country_id)", nativeQuery = true)
    List<String> findCountryNameWherePoopingOccurred(@Param("creatorId") String creatorId);
}
