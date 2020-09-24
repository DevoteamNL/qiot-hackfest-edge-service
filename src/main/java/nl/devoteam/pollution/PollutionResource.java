package nl.devoteam.pollution;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "pollution-resource")
public interface PollutionResource {

    @GET
    @Path("/pollution")
    @Produces("application/json")
    PollutionData getPollutionData();
}
