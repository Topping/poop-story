package poop.story.backend.domain.repository;

public interface PoopEventRepository {
//    Streamable<PoopEvent> findByCreator_externalId(String externalId);
//    @Query(value = "SELECT e.id, e.version, e.created_at, e.updated_at, e.location, e.pooper_id FROM poopstory.event AS e " +
//        "INNER JOIN (SELECT p.id, p.pooper_ext_ref FROM poopstory.pooper AS p WHERE p.pooper_ext_ref = :externalId) AS p " +
//        "ON e.pooper_id = p.id AND ST_MakeEnvelope (:xmin, :ymin, :xmax, :ymax, 4326) ~ location", nativeQuery = true)
////    @Query(value = "SELECT * FROM poopstory.event WHERE ST_MakeEnvelope (:xmin, :ymin, :xmax, :ymax, 4326) ~ location", nativeQuery = true)
//    Streamable<PoopEvent> findInBoundingBox(String externalId, double xmin, double ymin, double xmax, double ymax);
}
