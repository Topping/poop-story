package poop.story.backend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class GroupMemberKey implements Serializable {
    @Column(name = "group_id", nullable = false, updatable = false)
    private UUID groupId;
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    public GroupMemberKey() {}

    public GroupMemberKey(UUID groupId, UUID userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public UUID groupId() {
        return groupId;
    }

    public GroupMemberKey setGroupId(UUID groupId) {
        this.groupId = groupId;
        return this;
    }

    public UUID userId() {
        return userId;
    }

    public GroupMemberKey setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }
}
