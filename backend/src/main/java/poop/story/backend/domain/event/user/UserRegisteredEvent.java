package poop.story.backend.domain.event.user;

import poop.story.backend.domain.event.DomainEvent;

public record UserRegisteredEvent(
    String authSubject,
    String email
) implements DomainEvent {}
