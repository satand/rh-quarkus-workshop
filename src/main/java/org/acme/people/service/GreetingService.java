package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@Locale(Language.EN)
@ApplicationScoped
public class GreetingService implements Greeting {

    public String greeting(String name) {
        return "hello " + name;
    }
}