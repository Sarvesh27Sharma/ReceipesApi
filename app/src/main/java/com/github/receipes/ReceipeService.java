package com.github.receipes;

import com.github.receipes.search.ReceipeSearch;
import com.github.receipes.search.ReceipeSearchCriteria;
import com.github.receipes.search.ReceipeSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceipeService {

    private final ReceipeRepository receipeRepository;
    private final ReceipeSpecificationBuilder receipeSpecificationBuilder;
    private final ReceipeEntityMapper mapper;

    public List<Receipe> getAllReceipes() {
        return receipeRepository.findAll().stream()
                .map(mapper::mapEntityToReceipe)
                .collect(Collectors.toList());
    }

    public List<Receipe> searchReceipes(ReceipeSearch receipeSearch) {
        Specification<ReceipeEntity> searchSpecification = createSearchSpecification(receipeSearch);

        return receipeRepository.findAll(searchSpecification).stream()
                .map(mapper::mapEntityToReceipe)
                .collect(Collectors.toList());
    }

    public Receipe getReceipeByName(String receipeName) {
        return receipeRepository.findById(receipeName)
                .map(mapper::mapEntityToReceipe)
                .orElseThrow(() -> new NotFoundException("receipe with name [" + receipeName + "] can not be found"));
    }

    public Receipe addReceipe(Receipe receipe) {
        return saveAndRetrieveReceipe(receipe);
    }

    public Receipe updateReceipe(String receipeName, Receipe receipe) {
        boolean isReceipeExist = receipeRepository.existsById(receipeName);

        if (isReceipeExist) {
            return saveAndRetrieveReceipe(receipe);
        }

        throw new NotFoundException("receipe with name [" + receipeName + "] can not be found");
    }

    public void deleteReceipe(String receipeName) {
        receipeRepository.deleteById(receipeName);
    }

    private Receipe saveAndRetrieveReceipe(Receipe receipe) {
        ReceipeEntity entity = receipeRepository.save(mapper.mapReceipeToEntity(receipe));
        return mapper.mapEntityToReceipe(entity);
    }

    private Specification<ReceipeEntity> createSearchSpecification(ReceipeSearch receipeSearch) {
        List<ReceipeSearchCriteria> criteriaList = receipeSearch.getReceipeSearchCriteria();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(receipeSearch.getDataOption());
            });
        }
        return receipeSpecificationBuilder.createSpecification(criteriaList);
    }
}
