package poop.story.backend.application.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public final class GeoJsonPolygon {
    private final String type;
    private final List<List<List<Double>>> coordinates;

    @JsonCreator
    public GeoJsonPolygon(@JsonProperty("coordinates") List<List<List<Double>>> coordinates) {
        this.type = "Polygon";
        this.coordinates = coordinates;
    }

    @JsonProperty("type")
    public String type() {
        return type;
    }

    @JsonProperty("coordinates")
    public List<List<List<Double>>> coordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GeoJsonPolygon) obj;
        return Objects.equals(this.type, that.type) &&
            Objects.equals(this.coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, coordinates);
    }

    @Override
    public String toString() {
        return "GeoJsonPolygon[" +
            "type=" + type + ", " +
            "coordinates=" + coordinates + ']';
    }

}
