package com.github.receipes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApiTestData {
    INPUT_UPDATE_FILE("receipe-extra-ingredients.json"),
    INPUT_CREATE_FILE("receipe.json"),
    BAD_REQUEST_INPUT_CREATE_FILE("bad-request-receipe.json"),
    BAD_REQUEST_OUT_CREATE_FILE("bad-request-response.json"),
    RECEIPE_NAME("shahi-paneer"),
    INPUT_RECEIPE_SEARCH("receipe-search-input.json");
    private final String value;
}
