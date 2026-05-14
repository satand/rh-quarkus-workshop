package org.acme.people.health;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Provider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
@Readiness
public class DepHealthCheck implements HealthCheck {

    @ConfigProperty(name = "dep.file.status.path", defaultValue = "/tmp/dep.status")
    public String depStatusPath;

    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Dependency health check")
                .withData("file", depStatusPath);

        try {
            verifyDependency();
            responseBuilder.up();
        } catch (IllegalStateException e) {
            responseBuilder.down()
                    .withData("error", e.getMessage());
        }

        return responseBuilder.build();
    }

    private void verifyDependency() {

        try {
            String depStatus = Files.lines(Path.of(depStatusPath)).findFirst().orElse("").trim();
       
            if (!depStatus.equalsIgnoreCase("true")) {
                throw new IllegalStateException("Dependency status is not true");
            }
        } catch (IOException e) {

            throw new IllegalStateException("Cannot check dependency status file");
        }
    }
}