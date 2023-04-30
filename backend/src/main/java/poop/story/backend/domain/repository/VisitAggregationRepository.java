package poop.story.backend.domain.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import poop.story.backend.domain.visit.VisitCountryAggregation;

import java.util.List;

@Repository
public class VisitAggregationRepository {
    private static final String aggregationQuery = "SELECT common_name as country, COUNT(*) count FROM poopstory.visits " +
        "INNER JOIN poopstory.countries " +
        "ON (poopstory.countries.id = poopstory.visits.country_id) " +
        "GROUP BY country";
    private static final String visitorCount = "SELECT COUNT(DISTINCT creator_id) FROM poopstory.visits;";

    private final JdbcTemplate template;

    public VisitAggregationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<VisitCountryAggregation> countVisitByCountry() {
        return template.query(aggregationQuery, (rs, rowNum) -> new VisitCountryAggregation(rs.getString("country"), rs.getInt("count")));
    }

    public int uniqueVisitors() {
        return template.query(visitorCount, (ResultSetExtractor<Integer>) rs -> rs.getInt("count"));
    }
}
