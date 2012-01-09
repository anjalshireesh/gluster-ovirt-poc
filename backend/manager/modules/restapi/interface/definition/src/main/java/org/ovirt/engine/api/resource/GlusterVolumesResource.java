/**
 *
 */
package org.ovirt.engine.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.Formatted;
import org.ovirt.engine.api.model.GlusterVolume;
import org.ovirt.engine.api.model.GlusterVolumes;

/**
 *
 */
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
public interface GlusterVolumesResource {
    @GET
    @Formatted
    public GlusterVolumes list();

    /**
     * Creates a new GlusterVolume and adds it to the database. The GlusterVolume is created based on the properties of
     *
     * @volume. <p>
     *          The GlusterVolume#name, GlusterVolume#templateId and GlusterVolume#clusterId properties are required.
     * @param volume
     *            the GlusterVolume definition from which to create the new GlusterVolume
     * @return the new newly created GlusterVolume
     */
    @POST
    @Formatted
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_X_YAML })
    public Response add(GlusterVolume volume);

    @DELETE
    @Path("{name}")
    public Response remove(@PathParam("name") String name);

    /**
     * Sub-resource locator method, returns individual GlusterVolumeResource on which the remainder of the URI is
     * dispatched.
     *
     * @param name
     *            the GlusterVolume name
     * @return matching subresource if found
     */
    @Path("{name}")
    public GlusterVolumeResource getGlusterVolumeSubResource(@PathParam("name") String name);
}
