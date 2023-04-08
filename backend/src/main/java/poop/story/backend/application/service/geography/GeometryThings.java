package poop.story.backend.application.service.geography;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryThings {
    public double test() {
        var f = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        var topCoord = new Coordinate(56.193446, 10.114853);
        var bottomCoord = new Coordinate(56.181457, 10.144980);
        var coords = new Coordinate[] {topCoord, bottomCoord};
        var poly = f.createPolygon(coords);
        return poly.getArea();
    }
}
