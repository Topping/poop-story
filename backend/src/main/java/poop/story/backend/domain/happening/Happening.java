package poop.story.backend.domain.happening;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import poop.story.backend.application.model.happening.HappeningDTO;
import poop.story.backend.domain.user.UserAggregate;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "poopstory", name = "happenings")
public class Happening implements Serializable {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private UserAggregate creator;

    @Column(name = "location", nullable = false)
    private Point location;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "happening_time", nullable = false, updatable = false)
    private Instant happeningTime;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private HappeningType happeningType;

    protected Happening() {}

    public Happening(UserAggregate creator, HappeningDTO dto) {
        this.id = UUID.randomUUID();
        this.creator = creator;
        this.happeningTime = dto.when() == null ? Instant.now() : dto.when();
        this.happeningType = dto.type();
        var g = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        this.location = g.createPoint(new Coordinate(dto.lat(), dto.lon()));
    }

    public Happening(UserAggregate creator, double lat, double lon, Instant happeningTime, HappeningType type) {
        this.id = UUID.randomUUID();
        this.creator = creator;
        this.happeningTime = happeningTime == null ? Instant.now() : happeningTime;
        this.happeningType = type;

        var g = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        this.location = g.createPoint(new Coordinate(lat, lon));
    }

    public UUID id() {
        return id;
    }

    public UserAggregate creator() {
        return creator;
    }

    public Happening setCreator(UserAggregate creator) {
        this.creator = creator;
        return this;
    }

    public Point location() {
        return location;
    }

    public Happening setLocation(Point location) {
        this.location = location;
        return this;
    }

    public HappeningType happeningType() {
        return happeningType;
    }

    public Happening setHappeningType(HappeningType happeningType) {
        this.happeningType = happeningType;
        return this;
    }

    public Instant happeningTime() {
        return happeningTime;
    }

    public Happening setHappeningTime(Instant happeningTime) {
        this.happeningTime = happeningTime;
        return this;
    }
}
