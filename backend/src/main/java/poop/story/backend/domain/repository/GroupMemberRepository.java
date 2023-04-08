package poop.story.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poop.story.backend.domain.user.GroupMember;

import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {
}
