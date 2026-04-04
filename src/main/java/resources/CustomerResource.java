package resources;

import dtos.CustomerRegRequest;
import dtos.LoginRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.CustomerService;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService service;

    @POST
    @Path("/register")
    public Response register(CustomerRegRequest req) {
        return Response.ok(service.register(req)).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        return Response.ok(service.login(req.email, req.password)).build();
    }
}
