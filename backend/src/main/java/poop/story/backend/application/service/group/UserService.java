package poop.story.backend.application.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import poop.story.backend.application.model.happening.HappeningDTO;
import poop.story.backend.application.service.authz.UserInfoService;
import poop.story.backend.domain.happening.Happening;
import poop.story.backend.domain.happening.HappeningBySubjectSpecification;
import poop.story.backend.domain.repository.HappeningRepository;
import poop.story.backend.domain.repository.UserRepository;
import poop.story.backend.domain.user.UserAggregate;
import poop.story.backend.infrastructure.util.SecurityUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserInfoService userInfoService;
    private final UserRepository userRepository;
    private final HappeningRepository happeningRepository;

    @Autowired
    public UserService(UserInfoService userInfoService, UserRepository userRepository, HappeningRepository happeningRepository) {
        this.userInfoService = userInfoService;
        this.userRepository = userRepository;
        this.happeningRepository = happeningRepository;
    }

    public Optional<UserAggregate> getOrCreateAuthenticatedUser() {
        var user = getAuthenticatedUser();
        if (user.isPresent()) return user;

        return registerAuthenticatedUser();
    }

    public Optional<UserAggregate> getAuthenticatedUser() {
        return SecurityUtil.getAuthSubject()
            .flatMap(userRepository::findByAuthSubject);
    }

    public Optional<UserAggregate> registerAuthenticatedUser() {
        var userInfo = userInfoService.getUserInfo();
        return userInfo.flatMap(info -> SecurityUtil.getAuthSubject()
            .map(sub -> new UserAggregate(sub, info.email()))
            .map(userRepository::save));

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<UserAggregate> addNewHappening(HappeningDTO dto) {
        var u = SecurityUtil.getAuthSubject()
            .flatMap(userRepository::findByAuthSubject)
            .orElseGet(() -> registerAuthenticatedUser().orElseThrow());

        u.registerHappening(dto);
        return Optional.of(userRepository.save(u));
    }

    @Transactional
    public List<Happening> fetchHappenings() {
        var sub = SecurityUtil.getAuthSubject();
        if (sub.isEmpty()) return Collections.emptyList();

        var spec = new HappeningBySubjectSpecification(sub.get());
        return happeningRepository.findAll(spec);
    }

    @Transactional
    public List<Happening> fetchHappenings(double x1, double y1, double x2, double y2) {
        var sub = SecurityUtil.getAuthSubject();
        if (sub.isEmpty()) return Collections.emptyList();

        return happeningRepository.getHappeningsInboundingBox(sub.get(), x1, y1, x2, y2);
    }
}
