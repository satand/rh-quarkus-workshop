package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingServiceIT implements Greeting {

    public String greeting(String name) {
        return "ciao " + name;
    }

    @Override
    public String getLocale() {
        return "it";
    }

}