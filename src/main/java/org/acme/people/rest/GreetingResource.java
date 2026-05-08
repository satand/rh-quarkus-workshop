package org.acme.people.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.acme.people.service.Greeting;
import org.acme.people.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.arc.All;
import io.smallrye.common.annotation.NonBlocking;

@Path("/hello")
// @RequestScoped
// @ApplicationScoped
public class GreetingResource {

    public static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    @All
    List<Greeting> services;

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
        return services.stream()
            .filter(s -> locale.equals(s.getLocale()))
            .findFirst()
            .orElse(services.stream().filter(s -> "en".equals(s.getLocale())).findFirst().get())
            .greeting(name);
    }
}