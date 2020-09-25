package nl.devoteam.registration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "management-resource")
public interface ManagementServiceResource {

    @GET
    @Path("/admin/serial")
    @Produces("text/plain")
    String getSerial();
}
