package org.acme.people.rest;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.acme.people.service.Greeting;
import org.acme.people.service.Locale;
import org.acme.people.service.Locale.Language;
import org.acme.people.service.Locale.Literal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.NonBlocking;

@Path("/hello")
// @RequestScoped
// @ApplicationScoped
public class GreetingResource {

    public static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    @Any
    Instance<Greeting> greetings;

    @Inject
    @Locale(Language.EN)
    Greeting defaultGreeting;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String hello() {
        // log.info("----> {} {}", this);
        return "Hello from Quarkus REST";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    @NonBlocking
    public String greeting(@PathParam("name") String name, @QueryParam("locale") @DefaultValue("en") String locale) {
        try {
            Language language = Language.valueOf(locale.toUpperCase());
            Instance<Greeting> selected = greetings.select(Literal.of(language));
            if (selected.isResolvable()) {
                return selected.get().greeting(name);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Unknown locale: {} - using default", locale);
        }

        return defaultGreeting.greeting(name);
    }
}