package com.github.receipes;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static java.nio.file.Files.readString;

public class TestUtil {
    public static String convertJsonFileIntoString(String inputReceipeFile) throws IOException {
        return readString(new ClassPathResource(inputReceipeFile).getFile().toPath());
    }
}
