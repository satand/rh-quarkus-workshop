package org.acme.people.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.config.SmallRyeConfig;

@Path("/config")
public class ConfigInfoResource {

    @ConfigProperty(name = "app.info.version")
    String version;

    @ConfigProperty(name = "app.info.author")
    String author;

    @ConfigProperty(name = "custom.greeting")
    Optional<String> customGreeting;

    @Inject
    SmallRyeConfig config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @NonBlocking
    @Path("/info")
    public Map<String, Object> getConfigInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("app.info.version", version);
        info.put("app.info.author", author);
        config.getPropertyNames().forEach(name -> {
            if (name.startsWith("custom.") && !name.equals("custom.config.file")) {
                config.getOptionalValue(name, String.class)
                        .ifPresent(v -> info.put(name, v));
            }
        });
        return info;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sources")
    @NonBlocking
    public List<Object> getConfigSources() {

        return StreamSupport.stream(config.getConfigSources().spliterator(), false)
            .map(cs -> {
                Map<String, Object> source = new LinkedHashMap<>();
                source.put("name", cs.getName());
                source.put("ordinal", cs.getOrdinal());
                source.put("properties", cs.getProperties());
                return source;
            })
            .collect(Collectors.toList());
    }
}
