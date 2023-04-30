package poop.story.backend.application.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public record Location(double longitude, double latitude) {
    public static Point toPoint(Location location) {
        var fac = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        var coordinate = new Coordinate(location.longitude(), location.latitude());
        return fac.createPoint(coordinate);
    }

    public static Location fromPoint(Point point) {
        return new Location(point.getX(), point.getY());
    }
}
