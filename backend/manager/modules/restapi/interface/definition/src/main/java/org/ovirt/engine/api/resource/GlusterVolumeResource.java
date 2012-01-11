package org.ovirt.engine.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.Formatted;
import org.ovirt.engine.api.model.Action;
import org.ovirt.engine.api.model.Actionable;
import org.ovirt.engine.api.model.GlusterVolume;

@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
public interface GlusterVolumeResource {
    @GET
    @Formatted
    public GlusterVolume get();

    @Path("{action: (start|stop|rebalanceStart|rebalanceStop)}/{oid}")
    public ActionResource getActionSubresource(@PathParam("action") String action, @PathParam("oid") String oid);

    @Path("glusterbricks")
    public GlusterVolumeBricksResource getBricksResource();

    @POST
    @Formatted
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
    @Actionable
    @Path("start")
    public Response start(Action action);

    @POST
    @Formatted
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
    @Actionable
    @Path("stop")
    public Response stop(Action action);

    @POST
    @Formatted
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
    @Actionable
    @Path("rebalanceStart")
    public Response rebalanceStart(Action action);

    @POST
    @Formatted
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
    @Actionable
    @Path("rebalanceStop")
    public Response rebalanceStop(Action action);
}
