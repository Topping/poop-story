package poop.story.backend.application.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poop.story.backend.application.model.happening.HappeningDTO;
import poop.story.backend.application.service.group.UserService;
import poop.story.backend.domain.user.UserAggregate;
import poop.story.backend.infrastructure.io.BlockingIOSchedulerConfig;
import poop.story.backend.infrastructure.util.SecurityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/private/user")
public class UserController {
    private final Scheduler ioScheduler;
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier(BlockingIOSchedulerConfig.IO_SCHEDULER_BEAN) Scheduler ioScheduler, UserService userService) {
        this.ioScheduler = ioScheduler;
        this.userService = userService;
    }

    @GetMapping("/happening")
    public Flux<HappeningDTO> fetchMyHappenings() {
        return SecurityUtil.getAuthSubject()
            .publishOn(ioScheduler)
            .flatMapIterable(userService::fetchHappenings)
            .map(h -> new HappeningDTO(
                h.location().getX(),
                h.location().getY(),
                h.happeningType(),
                h.happeningTime()
            ));
    }

    @PostMapping("/happening")
    public Mono<UserAggregate> registerHappening(@RequestBody HappeningDTO dto) {
        return SecurityUtil.getAuthSubject()
            .publishOn(ioScheduler)
            .map(sub -> userService.addNewHappening(sub, dto))
            .filter(Optional::isPresent)
            .map(Optional::get);
    }
}
