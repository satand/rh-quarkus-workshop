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
import org.acme.people.service.GreetingAI;
import org.acme.people.service.Language;
import org.acme.people.service.Locale;
import org.acme.people.service.LocaleLiteral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.common.annotation.NonBlocking;

@Path("/hello/v2")
// @RequestScoped
// @ApplicationScoped
public class GreetingResourceV2 {

    public static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    GreetingAI greetingAI;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    @NonBlocking
    public String greeting(@PathParam("name") String name, @QueryParam("locale") @DefaultValue("en") String locale) {
        
        return greetingAI.greeting(name, Language.fromString(locale));
    }
}