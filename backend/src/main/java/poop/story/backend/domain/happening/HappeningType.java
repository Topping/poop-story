package poop.story.backend.domain.happening;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HappeningType {
    PEE("PEE"),
    POO("POO"),
    BOTH("BOTH");

    private final String happeningType;

    HappeningType(String happeningType) {
        this.happeningType = happeningType;
    }

    @JsonValue
    public String happeningType() {
        return happeningType;
    }
}
