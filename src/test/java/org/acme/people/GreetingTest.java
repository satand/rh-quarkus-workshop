package org.acme.people;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import org.acme.people.service.Greeting;
import org.acme.people.service.GreetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.arc.ClientProxy;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingTest {

    @Inject
    Instance<Greeting> services;

    @Test
    public void testGreetings() {
        
        if (services.isUnsatisfied()) {
            return;
        }
        
        // Assertions.assertTrue(services.select(GreetingService.class).get()
        //     .greeting("Quarkus").startsWith("hello Quarkus"));

        services.stream().forEach(s -> {
            switch (s.getLocale()) {
                case "en":
                    Assertions.assertTrue("hello Quarkus".equals(s.greeting("Quarkus")));
                    break;

                case "it":
                    Assertions.assertTrue("ciao Quarkus".equals(s.greeting("Quarkus")));
                    break;

                case "es":
                    Assertions.assertTrue("hola Quarkus".equals(s.greeting("Quarkus")));
                    break;
            
                default:
                    // throw new RuntimeException("Add a test case for your Greeting implementation: " + s.getClass().getName());
                    Object realInstance = ClientProxy.unwrap(s);
                    throw new RuntimeException("Add a test case for your Greeting implementation: " + realInstance.getClass().getName());
            }
        });
    }
}