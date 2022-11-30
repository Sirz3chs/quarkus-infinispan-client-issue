package org.acme;

import io.quarkus.infinispan.client.CacheResult;
import io.quarkus.infinispan.client.Remote;
import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.infinispan.client.hotrod.RemoteCache;
import org.jboss.logging.Logger;

@Path("/hello")
public class ReactiveGreetingResource {

    private static final Logger LOGGER = Logger.getLogger(ReactiveGreetingResource.class.getName());

    @Inject
    @Remote("foo")
    RemoteCache<String, String> cache;

    // KO - never ends
    @GET
    @Path("{user}")
    @Produces(MediaType.TEXT_PLAIN)
    @CacheResult(cacheName = "foo")
    public Uni<String> hello(@PathParam("user") final String user) {
        LOGGER.info("hello not getting from cache");
        return Uni.createFrom().item(String.format("Hello %s", user));
    }

    // KO - value is getting from cache but never put in cache
    // 2nd call fail - I don't know why, i don't have this behavior in my usage but only here in reproduction
    @GET
    @Path("{user}/2")
    @Produces(MediaType.TEXT_PLAIN)
    @CacheResult(cacheName = "foo", lockTimeout = 1000)
    public Uni<String> hello2(@PathParam("user") final String user) {
        LOGGER.info("hello2 not getting from cache");
        return Uni.createFrom().item(String.format("Hello %s", user));
    }

    // OK
    @GET
    @Path("{user}/3")
    @Produces(MediaType.TEXT_PLAIN)
    @CacheResult(cacheName = "foo")
    public String hello3(@PathParam("user") final String user) {
        LOGGER.info("hello3 not getting from cache");
        return String.format("Hello %s", user);
    }

    // OK - workaround
    // 2nd call fail - I don't know why, i don't have this behavior in my usage but only here in reproduction
    @GET
    @Path("{user}/4")
    @Produces(MediaType.TEXT_PLAIN)
    @CacheResult(cacheName = "foo", lockTimeout = 1000)
    public Uni<String> hello4(@PathParam("user") final String user) {
        LOGGER.info("hello4 not getting from cache");
        return Uni.createFrom().item(String.format("Hello %s", user))
            .call(message -> Uni.createFrom().completionStage(cache.putAsync(user, message)));
    }
}
