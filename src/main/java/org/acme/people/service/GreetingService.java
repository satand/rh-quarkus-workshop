package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@Gold
@ApplicationScoped
public class GreetingService implements Greeting {

    public String greeting(String name) {
        return "hello " + name;
    }

    @Override
    public String getLocale() {
        return "en";
    }

}