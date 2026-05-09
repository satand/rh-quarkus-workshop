package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@Locale(Locale.Language.IT)
@ApplicationScoped
public class GreetingServiceIT implements Greeting {

    public String greeting(String name) {
        return "ciao " + name;
    }

}