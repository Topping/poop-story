package poop.story.backend.application.model.happening;

import poop.story.backend.domain.happening.HappeningType;

import java.time.Instant;

public record HappeningDTO(double lat, double lon, HappeningType type, Instant when) {
    public HappeningDTO(double lat, double lon, HappeningType type) {
        this(lat, lon, type, Instant.now());
    }
}
