package org.acme.people;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.people.service.Greeting;
import org.acme.people.service.Locale;
import org.acme.people.service.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.arc.All;
import io.quarkus.arc.ClientProxy;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingsTest {

    public static final Logger log = LoggerFactory.getLogger(GreetingsTest.class);

    @Inject
    @All
    List<Greeting> greetings;

    @Inject
    @Any
    Instance<Greeting> greetingsInstance;


    @Test
    public void testGreetings() {
        
        if (greetings.isEmpty()) {
            return;
        }

        log.info("greetings: {}", greetings.stream().map(g -> g.getClass().getName()).collect(Collectors.toList()));

        greetings.stream().forEach(g -> {
            Object realInstance = ClientProxy.unwrap(g);
            Locale locale = realInstance.getClass().getAnnotation(Locale.class);

            testGreeting(g, locale);
        });

    }

    @Test
    public void testGreetingsInstance() {
        
        if (greetingsInstance.isUnsatisfied()) {
            return;
        }

        log.info("greetings: {}", greetingsInstance.stream().map(g -> g.getClass().getName()).collect(Collectors.toList()));

        greetingsInstance.handlesStream().forEach(hg -> {
            Locale locale = (Locale) hg.getBean().getQualifiers().stream().filter(q -> q instanceof Locale).findFirst().orElse(null);

            testGreeting(hg.get(), locale);
        });

    }


    private void testGreeting(Greeting greeting, Locale locale) {

        Language language = locale != null ? locale.value() : null;
        if (language == null) {
            throw new RuntimeException("No @Locale annotation found on: " + greeting.getClass().getName());
        }

        switch (language) {
            case EN, UNKNOWN:
                Assertions.assertTrue("hello Quarkus".equals(greeting.greeting("Quarkus")));
                break;
            case IT:
                Assertions.assertTrue("ciao Quarkus".equals(greeting.greeting("Quarkus")));
                break;
            case ES:
                Assertions.assertTrue("hola Quarkus".equals(greeting.greeting("Quarkus")));
                break;
            default:
                throw new RuntimeException("Add a test case for your Greeting implementation: " +
                greeting.getClass().getName());
        }
    }

}