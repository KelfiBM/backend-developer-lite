package org.banreservas.interview.paises;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.banreservas.interview.paises.dtos.fetchall.FetchAllPaisesDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/")
@RegisterRestClient
public interface PaisRestClient {

    @GET
    @Path("/all")
    List<FetchAllPaisesDto> fetchAll(@QueryParam("fields") String fields) throws InterruptedException, ExecutionException;
}
