package poop.story.backend.infrastructure.geography;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeographyBeanConfig {
    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
    }

    @Bean
    public GeoJsonReader geoJsonReader(GeometryFactory factory) {
        return new GeoJsonReader(factory);
    }
}
