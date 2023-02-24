package com.github.receipes;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


@Builder(toBuilder = true)
@Getter
@Jacksonized
@RequiredArgsConstructor
public class Receipe {

    @NotBlank(message = "Field: [name] is required")
    private final String name;

    @NotNull(message = "Field: [isVegetarian] is required")
    private final Boolean isVegetarian;

    @Valid
    private final List<String> ingredients;
    @Valid
    private final List<String> instructions;

    @NotNull(message = "Field: [serves] is required")
    @Min(1)
    private final long serves;
}
