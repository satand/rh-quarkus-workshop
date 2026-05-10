package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingAIService implements GreetingAI {

    @Override
    public String greeting(String name, Language language) {
        // A very complex AI greeting service :) 
        // In the test I need to mock this logic because it's too complex to test.
        switch (language) {
            case EN:
                return "hello " + name;
            case IT:
                return "ciao " + name;
            case ES:
                return "hola " + name;
            default:
                return "hello " + name;
        }
    }
}