package poop.story.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GroupVisibility {
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");

    private final String visibility;

    GroupVisibility(String visibility) {
        this.visibility = visibility;
    }

    @JsonValue
    public String getVisibility() {
        return visibility;
    }
}
