package ma.ensa.internHub.specifications;

import ma.ensa.internHub.domain.entities.Internship;
import ma.ensa.internHub.domain.entities.InternshipType;
import ma.ensa.internHub.domain.entities.WorkMode;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class InternshipSpecification {

    public static Specification<Internship> searchInternships(
            String title,
            String city,
            List<InternshipType> types,
            List<WorkMode> workModes,
            String paid) {

        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            // Title condition
            if (title != null && !title.isEmpty()) {
                String titlePattern = "%" + title.toLowerCase() + "%";

                // Title match
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), titlePattern);

                // Skills match with various patterns
                Join<Internship, String> skillsJoin = root.join("skills", JoinType.LEFT);

                // Basic pattern
                Predicate basicSkillPattern = criteriaBuilder.like(
                        criteriaBuilder.lower(skillsJoin), titlePattern);

                // Pattern with dots removed
                String noDotsPattern = "%" + title.toLowerCase().replace(".", "")
                        .replace("-", "").replace(" ", "") + "%";
                Predicate noDotsSkillPattern = criteriaBuilder.like(
                        criteriaBuilder.lower(skillsJoin), noDotsPattern);

                // Pattern with hyphens
                String hyphenPattern = "%" + title.toLowerCase().replace(".", "-")
                        .replace(" ", "-").replace("--", "-") + "%";
                Predicate hyphenSkillPattern = criteriaBuilder.like(
                        criteriaBuilder.lower(skillsJoin), hyphenPattern);

                // Pattern with dots
                String dotsPattern = "%" + title.toLowerCase().replace("-", ".")
                        .replace(" ", ".") + "%";
                Predicate dotsSkillPattern = criteriaBuilder.like(
                        criteriaBuilder.lower(skillsJoin), dotsPattern);

                // Combine all title and skill patterns with OR
                predicates.add(criteriaBuilder.or(
                        titlePredicate,
                        basicSkillPattern,
                        noDotsSkillPattern,
                        hyphenSkillPattern,
                        dotsSkillPattern));
            }

            // City condition
            if (city != null && !city.isEmpty()) {
                String cityPattern = "%" + city.toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("city")), cityPattern));
            }

            // Types condition
            if (types != null && !types.isEmpty()) {
                Join<Internship, InternshipType> tagsJoin = root.join("tags", JoinType.LEFT);
                predicates.add(tagsJoin.in(types));
            }

            // Work modes condition
            if (workModes != null && !workModes.isEmpty()) {
                predicates.add(root.get("workMode").in(workModes));
            }

            // Paid condition
            if (paid != null && !paid.equals("All")) {
                boolean isPaid = paid.equals("true");
                predicates.add(criteriaBuilder.equal(root.get("paid"), isPaid));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
