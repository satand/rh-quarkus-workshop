package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingServiceES implements Greeting {

    public String greeting(String name) {
        return "hola " + name;
    }

    @Override
    public String getLocale() {
        return "es";
    }

}