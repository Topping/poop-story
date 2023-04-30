package poop.story.backend.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import poop.story.backend.application.model.GeoJsonPolygon;
import poop.story.backend.application.model.visit.VisitDTO;
import poop.story.backend.application.service.visit.VisitService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/private/visit")
@Validated
public class VisitController {
    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public List<VisitDTO> fetchMyVisits(@RequestParam(value = "country", required = false) String countryISO2Code) {
        return visitService.fetchAllVisits(countryISO2Code);
    }

    @PostMapping
    public ResponseEntity<VisitDTO> registerVisit(@RequestBody VisitDTO dto) {
        var saved = visitService.saveVisit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public VisitDTO getVisit(@PathVariable(name = "id")String id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public VisitDTO removeVisit(@PathVariable(name = "id")String id) {
        return null;
    }

    @PostMapping("/bounding")
    public List<VisitDTO> findInBoundingBox(@RequestBody GeoJsonPolygon polygon) {
        return visitService.findWithinPolygon(polygon);
    }

}
