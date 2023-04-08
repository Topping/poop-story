package poop.story.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poop.story.backend.domain.user.UserAggregate;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<UserAggregate, UUID> {
    Optional<UserAggregate> findByAuthSubject(String authSubject);
}
