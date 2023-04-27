package poop.story.backend.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import poop.story.backend.application.model.happening.HappeningDTO;
import poop.story.backend.application.service.group.UserService;
import poop.story.backend.domain.user.UserAggregate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping("/api/private/user")
public class HappeningController {
    private final UserService userService;

    @Autowired
    public HappeningController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/happening")
    public List<HappeningDTO> fetchMyHappenings(@RequestParam(name = "x1", required = false) Double x1,
                                  @RequestParam(name = "y1", required = false) Double y1,
                                  @RequestParam(name = "x2", required = false) Double x2,
                                  @RequestParam(name = "y2", required = false) Double y2) {
        var nonNullParams = Stream.of(x1,y1,x2,y2).filter(Objects::nonNull).count();
        if (nonNullParams > 0 && nonNullParams < 4) { // If one is defined, they should all be defined
            throw HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Give all params or none", new HttpHeaders(), null, StandardCharsets.UTF_8);
        }

        if (nonNullParams == 0) {
            return userService.fetchHappenings().stream()
                .map(h -> new HappeningDTO(
                    h.location().getX(),
                    h.location().getY(),
                    h.happeningType(),
                    h.happeningTime()
                )).toList();
        } else {
            return userService.fetchHappenings(x1, y1, x2, y2).stream()
                .map(h -> new HappeningDTO(
                    h.location().getX(),
                    h.location().getY(),
                    h.happeningType(),
                    h.happeningTime()
                )).toList();
        }
    }

    @PostMapping("/happening")
    public UserAggregate registerHappening(@RequestBody HappeningDTO dto) {
        return userService.addNewHappening(dto)
            .orElseThrow(() -> HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "BAD", new HttpHeaders(), null, StandardCharsets.UTF_8));
    }
}
