package poop.story.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GroupRole {
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    private final String role;

    GroupRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String role() {
        return role;
    }
}
