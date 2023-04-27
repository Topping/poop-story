package poop.story.backend.application.model.happening;

import poop.story.backend.domain.happening.HappeningType;

import java.time.Instant;

public record HappeningDTO(double lat, double lon, float rating, HappeningType type, Instant when) {
}
