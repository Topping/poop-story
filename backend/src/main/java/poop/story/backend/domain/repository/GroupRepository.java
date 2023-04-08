package poop.story.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poop.story.backend.domain.user.GroupAggregate;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupAggregate, UUID> {
}
