package org.acme.people;

import jakarta.inject.Inject;

import org.acme.people.service.Locale;
import org.acme.people.service.Language;
import org.acme.people.service.GreetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingServiceTest {
    
    @Inject
    @Locale(Language.EN)
    GreetingService service;

    @Test
    public void testGreetingService() {
        Assertions.assertTrue(service.greeting("Quarkus").startsWith("hello Quarkus"));
    }
}