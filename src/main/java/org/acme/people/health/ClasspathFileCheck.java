package org.acme.people.health;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@ApplicationScoped
public class ClasspathFileCheck implements HealthCheck {

    public static final Logger log = LoggerFactory.getLogger(ClasspathFileCheck.class);

    @Override
    public HealthCheckResponse call() {
        
        boolean status;
        try {
            status = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("check.me") != null 
                    && 
                    Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream("check.me")
                    .available() > 0;
        } catch (IOException e) {
            status = false;
            log.error("Error checking classpath file", e);
        }


        return HealthCheckResponse.named("Classpath file check")
                .status(status)
                .withData("file", "check.me")
                .build();
    }
}
