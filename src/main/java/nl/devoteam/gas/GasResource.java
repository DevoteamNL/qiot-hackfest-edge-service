package nl.devoteam.gas;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "gas-resource")
public interface GasResource {

    @GET
    @Path("/gas")
    @Produces("application/json")
    GasData getGasData();

}
