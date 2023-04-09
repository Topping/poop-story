package poop.story.backend.domain.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import poop.story.backend.application.model.happening.HappeningDTO;
import poop.story.backend.domain.AuditedBaseEntity;
import poop.story.backend.domain.happening.Happening;
import poop.story.backend.domain.happening.Happening_;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "poopstory", name = "users")
public class UserAggregate extends AuditedBaseEntity<UserAggregate> {
    @Id
    private UUID id;

    @Column(name = "subject", updatable = false, nullable = false)
    private String authSubject;
    @Column(name = "email", nullable = false)
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = GroupAggregate_.CREATOR)
    private List<GroupAggregate> createdGroups;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = Happening_.CREATOR, cascade = CascadeType.ALL)
    private List<Happening> happenings;

    @SuppressWarnings("unused")
    protected UserAggregate() {}

    public UserAggregate(String authSubject, String email) {
        this.id = UUID.randomUUID();
        this.authSubject = authSubject;
        this.email = email;
    }

    public UserAggregate registerHappening(HappeningDTO happening) {
        if (this.happenings == null) {
            happenings = new ArrayList<>();
        }
        var h = new Happening(this,
            happening.lat(),
            happening.lon(),
            happening.when(),
            happening.type()
        );
        this.happenings.add(h);
        return this;
    }

    public UUID id() {
        return id;
    }

    public String authSubject() {
        return authSubject;
    }

    public String email() {
        return email;
    }

    public List<GroupAggregate> createdGroups() {
        return createdGroups;
    }

    public List<Happening> happenings() {
        return happenings;
    }
}
