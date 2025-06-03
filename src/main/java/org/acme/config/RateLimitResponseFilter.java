package org.acme.config;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class RateLimitResponseFilter implements ContainerResponseFilter {

    private static final int MAX_REQUESTS = 5;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS));
        responseContext.getHeaders().add("X-RateLimit-Remaining", "3"); // valor simulado
    }
}
