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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.Optional;

import org.acme.people.service.Greeting;
import org.acme.people.service.Language;
import org.acme.people.service.Locale;
import org.acme.people.service.LocaleLiteral;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
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
    Instance<Greeting> defaultGreeting;

    @ConfigProperty(name = "greeting.message", defaultValue = "Hello")
    String message;

    @ConfigProperty(name = "greeting.name")
    Optional<String> name;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String hello() {

        // return Response.status(Response.Status.OK)
        //     .entity("Hello from Quarkus REST")
        //     .header("X-Custom-Header", "my-value")
        //     .build();

        // return RestResponse.ResponseBuilder
        //     .ok("Hello from Quarkus REST")
        //     .header("X-Custom-Header", "my-value")
        //     .build();

        // log.info("----> {} {}", this);
        return message + " from " + name.orElse("Quarkus REST");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    @NonBlocking
    public String greeting(@PathParam("name") String name, @QueryParam("locale") @DefaultValue("en") String locale) {

        Instance<Greeting> greeting = greetings.select(LocaleLiteral.of(Language.fromString(locale)));

        if (greeting.isUnsatisfied()) {
            greeting = defaultGreeting;
        }
        
        return greeting.get().greeting(name);
    }

    @GET
    @Path("/lastletter/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String lastLetter(@PathParam("name") String name) {
        int len = name.length();
        String lastLetter = name.substring(len-1);
        log.info("Got last letter: " + lastLetter);
        return lastLetter;
    }

    @GET
    @Path("/lastLetterQueryParam")
    @Produces(MediaType.TEXT_PLAIN)
    @NonBlocking
    public String lastLetterQueryParam(@QueryParam("name") String name) {
        int len = name.length();
        String lastLetter = name.substring(len-1);
        log.info("Got last letter: " + lastLetter);
        return lastLetter;
    }

    @ServerExceptionMapper
    public Response handleIllegalArgument(NullPointerException e) {
        return Response.status(500)
                .entity(Map.of("error", e.getMessage()))
                .build();
    }
}