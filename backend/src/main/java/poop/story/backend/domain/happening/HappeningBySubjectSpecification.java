package poop.story.backend.domain.happening;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import poop.story.backend.domain.user.UserAggregate;
import poop.story.backend.domain.user.UserAggregate_;

public class HappeningBySubjectSpecification implements Specification<Happening>  {
    private final String subject;

    public HappeningBySubjectSpecification(String subject) {
        this.subject = subject;
    }

    @Override
    public Specification<Happening> and(Specification<Happening> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Happening> or(Specification<Happening> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Happening> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Happening, UserAggregate> join = root.join(Happening_.CREATOR, JoinType.INNER);
        return criteriaBuilder.equal(
            join.get(UserAggregate_.AUTH_SUBJECT), subject
        );
    }
}
