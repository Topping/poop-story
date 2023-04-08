package poop.story.backend.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import poop.story.backend.domain.happening.Happening;

import java.util.List;
import java.util.UUID;

public interface HappeningRepository extends JpaRepository<Happening, UUID>, JpaSpecificationExecutor<Happening> {
}
