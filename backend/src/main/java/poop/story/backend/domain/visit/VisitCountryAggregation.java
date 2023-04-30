package poop.story.backend.domain.visit;

import org.locationtech.jts.geom.Geometry;

public record VisitCountryAggregation(String country, int count) {}
