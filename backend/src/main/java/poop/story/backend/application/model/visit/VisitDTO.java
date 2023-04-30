package poop.story.backend.application.model.visit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.validation.annotation.Validated;
import poop.story.backend.application.model.Location;
import poop.story.backend.domain.visit.VisitType;

import java.time.Instant;
import java.util.UUID;

@Validated
public record VisitDTO(
    @Null UUID id,
    VisitType type,
    Instant when,
    @NotNull Location location,
    float rating) {

    public VisitDTO(VisitType type,
                        Instant when,
                        @NotNull Location location,
                        float rating) {
        this(null, type, when, location,rating);
    }
}
