package com.github.receipes.search;

import com.github.receipes.ReceipeEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ReceipeSpecification implements Specification<ReceipeEntity> {
    private final ReceipeSearchCriteria receipeSearchCriteria;

    public ReceipeSpecification(final ReceipeSearchCriteria
                                        receipeSearchCriteria) {
        super();
        this.receipeSearchCriteria = receipeSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<ReceipeEntity> root,
                                 CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = receipeSearchCriteria.getValue()
                .toString().toLowerCase();

        return switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(receipeSearchCriteria.getOperation()))) {
            case CONTAINS -> cb.like(cb.lower(root.get(receipeSearchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN -> cb.notLike(cb.lower(root.get(receipeSearchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case BEGINS_WITH -> cb.like(cb.lower(root.get(receipeSearchCriteria.getFilterKey())), strToSearch + "%");
            case DOES_NOT_BEGIN_WITH -> cb.notLike(cb.lower(root.get(receipeSearchCriteria.getFilterKey())), strToSearch + "%");
            case ENDS_WITH -> cb.like(cb.lower(root.get(receipeSearchCriteria.getFilterKey())), "%" + strToSearch);
            case DOES_NOT_END_WITH -> cb.notLike(cb.lower(root.get(receipeSearchCriteria.getFilterKey())), "%" + strToSearch);
            case EQUAL -> cb.equal(root.get(receipeSearchCriteria.getFilterKey()), receipeSearchCriteria.getValue());
            case NOT_EQUAL -> cb.notEqual(root.get(receipeSearchCriteria.getFilterKey()), receipeSearchCriteria.getValue());
            case NUL -> cb.isNull(root.get(receipeSearchCriteria.getFilterKey()));
            case NOT_NULL -> cb.isNotNull(root.get(receipeSearchCriteria.getFilterKey()));
            case GREATER_THAN -> cb.greaterThan(root.get(receipeSearchCriteria.getFilterKey()), receipeSearchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL -> cb.greaterThanOrEqualTo(root.get(receipeSearchCriteria.getFilterKey()), receipeSearchCriteria.getValue().toString());
            case LESS_THAN -> cb.lessThan(root.get(receipeSearchCriteria.getFilterKey()), receipeSearchCriteria.getValue().toString());
            case LESS_THAN_EQUAL -> cb.lessThanOrEqualTo(root.get(receipeSearchCriteria.getFilterKey()), receipeSearchCriteria.getValue().toString());
            default -> null;
        };
    }
}
