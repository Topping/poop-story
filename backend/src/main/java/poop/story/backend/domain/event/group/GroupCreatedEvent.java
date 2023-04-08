package poop.story.backend.domain.event.group;

import poop.story.backend.domain.event.DomainEvent;
import poop.story.backend.domain.user.GroupAggregate;
import poop.story.backend.domain.user.UserAggregate;

public record GroupCreatedEvent(
    UserAggregate creator,
    GroupAggregate createdCroup)
implements DomainEvent {}
