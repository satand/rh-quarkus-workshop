package org.acme.people.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class ClasspathFileCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        boolean exists = Thread.currentThread()
                .getContextClassLoader()
                .getResource("check.me") != null;

        return HealthCheckResponse.named("Classpath file check")
                .status(exists)
                .withData("file", "check.me")
                .build();
    }
}
