package nl.devoteam.registration;


import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/register")
@RegisterRestClient(configKey = "registration-resource")
public interface RegistrationResource {

    @PUT
    @Path("/serial/{serial}/name/{name}/longitude/{longitude}/latitude/{latitude}")
    @Produces("text/plain")
    public String registerDevice(@PathParam String serial, @PathParam String name, @PathParam double longitude,
        @PathParam double latitude);


    @DELETE
    @Path("/id/{id}")
    @Produces("text/plain")
    public String deregisterDevice(@PathParam int id);
}
