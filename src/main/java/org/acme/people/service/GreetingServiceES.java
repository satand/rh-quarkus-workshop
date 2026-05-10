package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@Locale(Language.ES)
@ApplicationScoped
public class GreetingServiceES implements Greeting {

    public String greeting(String name) {
        return "hola " + name;
    }

}