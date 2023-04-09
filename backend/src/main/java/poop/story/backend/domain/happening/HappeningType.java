package poop.story.backend.domain.happening;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HappeningType {
    PEE("PEE"),
    POO("POO"),
    BOTH("BOTH");

    private final String type;

    HappeningType(String type) {
        this.type = type;
    }

    @JsonValue
    public String happeningType() {
        return type;
    }
}
