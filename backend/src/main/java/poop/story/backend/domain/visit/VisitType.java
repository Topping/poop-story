package poop.story.backend.domain.visit;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VisitType {
    PEE("PEE"),
    POO("POO"),
    BOTH("BOTH");

    private final String type;

    VisitType(String type) {
        this.type = type;
    }

    @JsonValue
    public String visitType() {
        return type;
    }
}
