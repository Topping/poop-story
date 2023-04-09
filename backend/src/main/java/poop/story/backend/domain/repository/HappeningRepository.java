package poop.story.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import poop.story.backend.domain.happening.Happening;

import java.util.List;
import java.util.UUID;

public interface HappeningRepository extends JpaRepository<Happening, UUID>, JpaSpecificationExecutor<Happening> {
    @Query(value = "SELECT h.id, h.happening_time, h.type, h.location, h.creator_id FROM poopstory.happenings AS h " +
        "INNER JOIN (SELECT u.id, u.subject FROM poopstory.users AS u WHERE u.subject = :subject) AS u " +
        "ON h.creator_id = u.id AND ST_MakeEnvelope (:x1, :y1, :x2, :y2, 4326) ~ location", nativeQuery = true)
    List<Happening> getHappeningsInboundingBox(String subject, double x1, double y1, double x2, double y2);
}
