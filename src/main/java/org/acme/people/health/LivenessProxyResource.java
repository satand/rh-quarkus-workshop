package org.acme.people.health;

import io.smallrye.health.SmallRyeHealth;
import io.smallrye.health.SmallRyeHealthReporter;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/internal/live")
public class LivenessProxyResource {

    @Inject
    SmallRyeHealthReporter reporter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response liveness() {
        SmallRyeHealth health = reporter.getLiveness();
        if (health.isDown()) {
            return Response.status(503).entity(health.getPayload()).build();
        }
        return Response.ok(health.getPayload()).build();
    }
}
