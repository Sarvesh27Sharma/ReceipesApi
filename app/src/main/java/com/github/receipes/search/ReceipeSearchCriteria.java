package com.github.receipes.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Setter
@Getter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ReceipeSearchCriteria {

    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public ReceipeSearchCriteria(String filterKey, String operation,
                                 Object value) {
        super();
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }
}
