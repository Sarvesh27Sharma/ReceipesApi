package com.github.receipes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.receipes.ApiTestData.BAD_REQUEST_INPUT_CREATE_FILE;
import static com.github.receipes.ApiTestData.BAD_REQUEST_OUT_CREATE_FILE;
import static com.github.receipes.ApiTestData.INPUT_CREATE_FILE;
import static com.github.receipes.ApiTestData.INPUT_RECEIPE_SEARCH;
import static com.github.receipes.ApiTestData.INPUT_UPDATE_FILE;
import static com.github.receipes.ApiTestData.RECEIPE_NAME;
import static com.github.receipes.ReceipeControllerTest.ApiUrl.RECEIPE_API_URL;
import static com.github.receipes.ReceipeControllerTest.ApiUrl.RECEIPE_API_WITH_URI_URL;
import static com.github.receipes.ReceipeControllerTest.ApiUrl.RECEIPE_SEARCH_API_URL;
import static com.github.receipes.TestUtil.convertJsonFileIntoString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: add more tests
@IntegrationTest
@DisplayName("Receipes api")
class ReceipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReceipeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReceipeEntityMapper mapper;

    @Test
    @DisplayName("Create a new receipe")
    void should_create_new_receipe() throws Exception {
        final String receipeToCreate = convertJsonFileIntoString(INPUT_CREATE_FILE.getValue());
        mockMvc.perform(post(RECEIPE_API_URL.getValue())
                .content(receipeToCreate)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(RECEIPE_NAME.getValue()));
    }

    @Test
    @DisplayName("Create a new receipe throws Bad Request")
    void should_throw_bad_request() throws Exception {
        final String receipeToCreate = convertJsonFileIntoString(BAD_REQUEST_INPUT_CREATE_FILE.getValue());
        final String errorResponse = convertJsonFileIntoString(BAD_REQUEST_OUT_CREATE_FILE.getValue());
        mockMvc.perform(post(RECEIPE_API_URL.getValue())
                .content(receipeToCreate)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json(errorResponse));
    }

    @Test
    @DisplayName("Retrieve an existing receipe")
    void should_return_a_receipe() throws Exception {
        final String receipeToCreate = convertJsonFileIntoString(INPUT_CREATE_FILE.getValue());
        insertData(objectMapper.readValue(receipeToCreate, Receipe.class));

        mockMvc.perform(get(RECEIPE_API_WITH_URI_URL.getValue(), RECEIPE_NAME.getValue())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(RECEIPE_NAME.getValue()));
    }

    @Test
    @DisplayName("Update an existing receipe")
    void should_update_an_existing_receipe() throws Exception {
        final String receipeToCreate = convertJsonFileIntoString(INPUT_CREATE_FILE.getValue());
        insertData(objectMapper.readValue(receipeToCreate, Receipe.class));

        final String receipeToUpdate = convertJsonFileIntoString(INPUT_UPDATE_FILE.getValue());

        mockMvc.perform(put(RECEIPE_API_WITH_URI_URL.getValue(), RECEIPE_NAME.getValue())
                .content(receipeToUpdate)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(RECEIPE_NAME.getValue()));
    }

    @Test
    @DisplayName("Update a non-existing receipe")
    void should_update_a_non_existing_receipe() throws Exception {
        final String receipeToUpdate = convertJsonFileIntoString(INPUT_UPDATE_FILE.getValue());

        mockMvc.perform(put(RECEIPE_API_WITH_URI_URL.getValue(), RECEIPE_NAME.getValue())
                .content(receipeToUpdate)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete an existing receipe")
    void should_delete_an_existing_receipe() throws Exception {
        final String receipeToCreate = convertJsonFileIntoString(INPUT_CREATE_FILE.getValue());
        insertData(objectMapper.readValue(receipeToCreate, Receipe.class));

        mockMvc.perform(delete(RECEIPE_API_WITH_URI_URL.getValue(), RECEIPE_NAME.getValue())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Create a new receipe search")
    void should_create_new_receipe_search() throws Exception {
        final String receipeRequest = convertJsonFileIntoString(INPUT_CREATE_FILE.getValue());
        insertData(objectMapper.readValue(receipeRequest, Receipe.class));

        final String receipeSearchRequest = convertJsonFileIntoString(INPUT_RECEIPE_SEARCH.getValue());

        mockMvc.perform(post(RECEIPE_SEARCH_API_URL.getValue())
                .content(receipeSearchRequest)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    private void insertData(Receipe receipe) {
        repository.save(mapper.mapReceipeToEntity(receipe));
    }

    @RequiredArgsConstructor
    @Getter
    enum ApiUrl {
        RECEIPE_API_URL("/api/v1/receipe"),
        RECEIPE_SEARCH_API_URL("/api/v1/receipe/search"),
        RECEIPE_API_WITH_URI_URL("/api/v1/receipe/{name}");
        private final String value;
    }
}