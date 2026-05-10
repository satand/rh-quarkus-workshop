package org.acme.people;

import jakarta.inject.Inject;

import org.acme.people.service.Language;
import org.acme.people.service.GreetingAI;
import org.acme.people.service.GreetingAIService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingAIServiceTest {
    
    // @Mock
    // public static class GreetingAIServiceMock implements GreetingAI {

    //     @Override
    //     public String greeting(String name, Language language) {
            
    //         return "hi " + name + " from mock";
    //     }
    // }

    // @Inject
    // GreetingAI service;

    @Mock
    public static class GreetingAIServiceMock extends GreetingAIService {

        @Override
        public String greeting(String name, Language language) {
            
            return "hi " + name + " from mock";
        }
    }

    @Inject
    GreetingAIService service;

    @Test
    public void testGreetingAIService() {

        Assertions.assertTrue(service.greeting("Quarkus", Language.EN).startsWith("hi Quarkus from mock"));
    }
}