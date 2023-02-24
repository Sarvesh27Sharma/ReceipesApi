package com.github.receipes.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ReceipeSearch {

    private List<ReceipeSearchCriteria> receipeSearchCriteria;
    private String dataOption;
}