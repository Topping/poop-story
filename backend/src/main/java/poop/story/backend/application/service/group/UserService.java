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

    public Optional<UserAggregate> getOrCreateAuthenticatedUser(String subject) {
        var user = getAuthenticatedUser(subject);
        if (user.isPresent()) return user;

        var userInfo = userInfoService.getUserInfo().blockOptional();
        if (userInfo.isEmpty()) return Optional.empty();

        var newUser = new UserAggregate(subject, userInfo.get().email());
        return Optional.of(userRepository.save(newUser));
    }

    public Optional<UserAggregate> getAuthenticatedUser(String subject) {
        return userRepository.findByAuthSubject(subject);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<UserAggregate> addNewHappening(String thisSubject, HappeningDTO dto) {
        var u = userRepository.findByAuthSubject(thisSubject);
        if (u.isEmpty()) return Optional.empty();
        var user = u.get();
        user.registerHappening(dto);
        return Optional.of(userRepository.save(user));
    }

    @Transactional
    public List<Happening> fetchHappenings(String subject) {
        var spec = new HappeningBySubjectSpecification(subject);
        return happeningRepository.findAll(spec);
    }
}
