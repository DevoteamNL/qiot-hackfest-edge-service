package nl.devoteam.pollution;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/pollution")
@RegisterRestClient(configKey = "pollution-resource")
public interface PollutionResource {

    @GET
    @Path("")
    @Produces("application/json")
    public PollutionData getPollutionData();
}
