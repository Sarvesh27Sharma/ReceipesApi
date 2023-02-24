package com.github.receipes;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = App.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
@Tag("integration")
@AutoConfigureMockMvc
public @interface IntegrationTest {
}
