package poop.story.backend.application.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poop.story.backend.domain.repository.GroupRepository;
import poop.story.backend.domain.user.GroupAggregate;
import poop.story.backend.domain.user.GroupVisibility;

import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public Optional<GroupAggregate> createNewGroup(String subject, String groupName) {
        return userService.getOrCreateAuthenticatedUser(subject)
            .map(user -> new GroupAggregate(user, groupName, GroupVisibility.PRIVATE))
            .map(groupRepository::save);
    }
}
