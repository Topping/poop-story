package poop.story.backend.domain.visit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "poopstory", name = "visits")
public class Visit implements Serializable {
    @Id
    private UUID id;

    @Column(name = "country_id", nullable = false)
    private UUID countryId;

    @Column(name = "creator_id", nullable = false)
    private String creatorId;

    @Column(name = "location", nullable = false)
    private Point location;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "visit_time", nullable = false, updatable = false)
    private Instant visitTime;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    protected Visit() {}

    public Visit(UUID countryId, String creatorId, Point location, Instant visitTime, VisitType type) {
        this.id = UUID.randomUUID();
        this.countryId = countryId;
        this.creatorId = creatorId;
        this.visitTime = visitTime == null ? Instant.now() : visitTime;
        this.visitType = type;
        this.location = location;
    }

    public UUID id() {
        return id;
    }

    public UUID countryId() {
        return countryId;
    }

    public Visit setCountryId(UUID countryId) {
        this.countryId = countryId;
        return this;
    }

    public Point location() {
        return location;
    }

    public Visit setLocation(Point location) {
        this.location = location;
        return this;
    }

    public VisitType visitType() {
        return visitType;
    }

    public Visit setVisitType(VisitType visitType) {
        this.visitType = visitType;
        return this;
    }

    public Instant visitTime() {
        return visitTime;
    }

    public Visit setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
        return this;
    }

    public String creatorId() {
        return creatorId;
    }

    public Visit setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public float rating() {
        return rating;
    }

    public Visit setRating(float rating) {
        this.rating = rating;
        return this;
    }
}
