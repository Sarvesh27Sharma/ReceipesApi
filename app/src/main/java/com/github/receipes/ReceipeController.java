package com.github.receipes;

import com.github.receipes.search.ReceipeSearch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ReceipeController {

    private final ReceipeService receipeService;

    @GetMapping("/receipe")
    @ResponseStatus(OK)
    public List<Receipe> getAllReceipes() {
        return receipeService.getAllReceipes();
    }

    @PostMapping("/receipe/search")
    @ResponseStatus(OK)
    public List<Receipe> searchReceipes(@RequestBody ReceipeSearch receipeSearch) {
        return receipeService.searchReceipes(receipeSearch);
    }

    @GetMapping("/receipe/{name}")
    @ResponseStatus(OK)
    public Receipe getReceipeByName(@PathVariable(value = "name") String receipeName) {
        return receipeService.getReceipeByName(receipeName);
    }

    @PostMapping("/receipe")
    @ResponseStatus(CREATED)
    public Receipe addReceipe(@Valid @RequestBody Receipe receipe) {
        return receipeService.addReceipe(receipe);
    }

    @PutMapping("/receipe/{name}")
    @ResponseStatus(OK)
    public Receipe updateReceipe(@PathVariable(value = "name") String receipeName,
                                 @Valid @RequestBody Receipe receipe) {
        return receipeService.updateReceipe(receipeName, receipe);
    }

    @DeleteMapping("/receipe/{name}")
    @ResponseStatus(NO_CONTENT)
    public void deleteReceipe(@PathVariable(value = "name") String receipeName) {
        receipeService.deleteReceipe(receipeName);
    }
}
