package poop.story.backend.application.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poop.story.backend.application.service.geography.GeometryThings;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/private/test")
public class TestController {

    @GetMapping("/area")
    public Mono<Double> getTestArea() {
        var x = new GeometryThings();
        return Mono.fromSupplier(() -> x.test());
    }
}
