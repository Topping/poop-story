package poop.story.backend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import poop.story.backend.domain.AuditedBaseEntity;
import poop.story.backend.domain.event.group.GroupCreatedEvent;

import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "poopstory", name = "groups")
public class GroupAggregate extends AuditedBaseEntity<GroupAggregate> {
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String groupName;

    @Column(name = "visibility", nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupVisibility visibility;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = GroupMember_.GROUP)
    private List<GroupMember> members;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private UserAggregate creator;

    @SuppressWarnings("unused")
    protected GroupAggregate() {}

    public GroupAggregate(UserAggregate creator, String groupName, GroupVisibility visibility) {
        this.id = UUID.randomUUID();
        this.groupName = groupName;
        this.visibility = visibility;
        this.creator = creator;
        this.registerEvent(new GroupCreatedEvent(creator, this));
    }

    public UUID id() {
        return id;
    }

    public String groupName() {
        return groupName;
    }

    public GroupAggregate setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public GroupVisibility visibility() {
        return visibility;
    }

    public GroupAggregate setVisibility(GroupVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public List<GroupMember> members() {
        return members;
    }

    public GroupAggregate setMembers(List<GroupMember> members) {
        this.members = members;
        return this;
    }
}
