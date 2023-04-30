package poop.story.backend.domain.country;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.locationtech.jts.geom.MultiPolygon;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(schema = "poopstory", name = "countries")
public class Country implements Serializable {
    @Id
    private UUID id;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "iso3_code")
    private String iso3Code;

    @Column(name = "iso2_code")
    private String iso2Code;

    @Column(name = "boundary", columnDefinition = "geography(MultiPolygon, 4326)")
    private MultiPolygon boundary;

    protected Country() {}

    public Country(String commonName, String iso3Code, String iso2Code, MultiPolygon boundary) {
        this.id = UUID.randomUUID();
        this.commonName = commonName;
        this.iso3Code = iso3Code;
        this.iso2Code = iso2Code;
        this.boundary = boundary;
    }

    public UUID id() {
        return id;
    }

    public Country setId(UUID id) {
        this.id = id;
        return this;
    }

    public String commonName() {
        return commonName;
    }

    public Country setCommonName(String commonName) {
        this.commonName = commonName;
        return this;
    }

    public String iso3Code() {
        return iso3Code;
    }

    public Country setIso3Code(String iso3Code) {
        this.iso3Code = iso3Code;
        return this;
    }

    public String iso2Code() {
        return iso2Code;
    }

    public Country setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
        return this;
    }

    public MultiPolygon boundary() {
        return boundary;
    }

    public Country setBoundary(MultiPolygon boundary) {
        this.boundary = boundary;
        return this;
    }
}
