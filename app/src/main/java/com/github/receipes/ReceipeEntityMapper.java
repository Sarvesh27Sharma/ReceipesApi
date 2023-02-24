package com.github.receipes;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.join;

@Mapper(componentModel = "spring")
public interface ReceipeEntityMapper {

    @Mapping(target = "ingredients", source = "ingredients")
    ReceipeEntity mapReceipeToEntity(Receipe receipe);

    @Mapping(target = "ingredients", source = "ingredients")
    Receipe mapEntityToReceipe(ReceipeEntity receipe);

    default String mapIngredientsFromListToCommaSeperatedString(List<String> ingredients) {
        return join(",", ingredients);
    }

    default List<String> mapIngredientsFromCommaSeperatedStringToList(String ingredients) {
        return Stream.of(ingredients.split(",", -1))
                .collect(Collectors.toList());
    }
}
