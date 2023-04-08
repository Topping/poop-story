package poop.story.backend.domain.event.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import poop.story.backend.domain.repository.GroupMemberRepository;
import poop.story.backend.domain.user.GroupMember;
import poop.story.backend.domain.user.GroupRole;

@Component
public class GroupEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(GroupEventListener.class);
    private final GroupMemberRepository memberRepository;

    @Autowired
    public GroupEventListener(GroupMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void addCreatorAsMember(GroupCreatedEvent event) {
        LOG.info("Created group: {}, Creator: {}", event.createdCroup().id(), event.creator().authSubject());
        var gm = new GroupMember(event.createdCroup(), event.creator(), GroupRole.ADMIN);
        memberRepository.save(gm);
    }
}
