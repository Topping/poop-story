package poop.story.backend.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedBaseEntity<A extends AbstractAggregateRoot<A>> extends AbstractAggregateRoot<A> implements Serializable {
    @Version
    private Long version;

    public @NotNull Optional<Long> getVersion() {
        return Optional.ofNullable(version);
    }

    protected void setVersion(@Nullable Long version) {
        this.version = version;
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
