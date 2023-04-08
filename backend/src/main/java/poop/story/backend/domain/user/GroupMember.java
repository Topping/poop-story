package poop.story.backend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import poop.story.backend.domain.ValueObject;

import java.util.UUID;

@Entity
@Table(schema = "poopstory", name = "group_members")
@IdClass(GroupMemberKey.class)
public class GroupMember implements ValueObject {
    @Id
    @Column(name = "group_id")
    private UUID groupId;
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupAggregate group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAggregate user;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupRole role;

    @SuppressWarnings("unused")
    protected GroupMember() {}

    public GroupMember(GroupAggregate group, UserAggregate user, GroupRole role) {
        this.groupId = group.id();
        this.userId = user.id();
        this.role = role;
    }

    public UUID groupId() {
        return groupId;
    }

    public GroupMember setGroupId(UUID groupId) {
        this.groupId = groupId;
        return this;
    }

    public UUID userId() {
        return userId;
    }

    public GroupMember setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public GroupAggregate group() {
        return group;
    }

    public GroupMember setGroup(GroupAggregate group) {
        this.group = group;
        return this;
    }

    public UserAggregate user() {
        return user;
    }

    public GroupMember setUser(UserAggregate user) {
        this.user = user;
        return this;
    }

    public GroupRole role() {
        return role;
    }

    public GroupMember setRole(GroupRole role) {
        this.role = role;
        return this;
    }
}
