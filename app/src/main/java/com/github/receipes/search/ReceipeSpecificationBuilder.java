package com.github.receipes.search;

import com.github.receipes.ReceipeEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.github.receipes.search.SearchOperation.ALL;

@Service
public class ReceipeSpecificationBuilder {

    //TODO: refactor code to more functional style
    public Specification<ReceipeEntity> createSpecification(final List<ReceipeSearchCriteria> params) {
        if (params.size() == 0) {
            return null;
        }

        Specification<ReceipeEntity> result = new ReceipeSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            ReceipeSearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == ALL ?
                    Specification.where(result).and(new ReceipeSpecification(criteria))
                    : Specification.where(result).or(new ReceipeSpecification(criteria));
        }
        return result;
    }
}
