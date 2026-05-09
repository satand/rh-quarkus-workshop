package org.acme.people;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import org.acme.people.service.Greeting;
import org.acme.people.service.Locale;
import org.acme.people.service.Locale.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.arc.ClientProxy;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingTest {

    @Inject
    @Any
    Instance<Greeting> greetings;

    @Test
    public void testGreetings() {
        
        if (greetings.isUnsatisfied()) {
            return;
        }

        greetings.stream().forEach(g -> {
            Object realInstance = ClientProxy.unwrap(g);
            Locale localeAnnotation = realInstance.getClass().getAnnotation(Locale.class);
            Language localeValue = localeAnnotation != null ? localeAnnotation.value() : null;

            if (localeValue == null) {
                throw new RuntimeException("No @Locale annotation found on: " + realInstance.getClass().getName());
            }

            switch (localeValue) {
                case EN:
                    Assertions.assertTrue("hello Quarkus".equals(g.greeting("Quarkus")));
                    break;
                case IT:
                    Assertions.assertTrue("ciao Quarkus".equals(g.greeting("Quarkus")));
                    break;
                case ES:
                    Assertions.assertTrue("hola Quarkus".equals(g.greeting("Quarkus")));
                    break;
                default:
                    throw new RuntimeException("Add a test case for your Greeting implementation: " +
                            realInstance.getClass().getName());
            }
        });

    }
}