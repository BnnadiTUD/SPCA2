package resources;

import dtos.LoginRequest;
import dtos.AdminLoginResponse;
import dtos.AdminRegisterRequest;
import dtos.ItemRequest;
import dtos.ItemResponse;
import dtos.StockUpdateRequest;
import services.AdminFacade;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

	@Inject
	AdminFacade adminFacade;

    @GET
    @Path("/items")
    public List<ItemResponse> getAllItems(
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortDirection") String sortDirection) {
        return adminFacade.getAllItems(sortBy, sortDirection);
    }

    @GET
    @Path("/items/{id}")
    public ItemResponse getItemById(@PathParam("id") Long id) {
        return adminFacade.getItemById(id);
    }

    @GET
    @Path("/items/search")
    public List<ItemResponse> searchItems(
            @QueryParam("title") String title,
            @QueryParam("manufacturer") String manufacturer,
            @QueryParam("category") String category,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortDirection") String sortDirection) {
        return adminFacade.searchItems(title, manufacturer, category, sortBy, sortDirection);
    }

    @POST
    @Path("/items")
    public Response createItem(@Valid ItemRequest request) {
        ItemResponse createdItem = adminFacade.createItem(request);
        return Response.status(Response.Status.CREATED).entity(createdItem).build();
    }

    @PUT
    @Path("/items/{id}")
    public ItemResponse updateItem(@PathParam("id") Long id, @Valid ItemRequest request) {
        return adminFacade.updateItem(id, request);
    }

    @DELETE
    @Path("/items/{id}")
    public Response deleteItem(@PathParam("id") Long id) {
    	adminFacade.deleteItem(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/items/{id}/stock")
    public ItemResponse updateStock(@PathParam("id") Long id, @Valid StockUpdateRequest request) {
        return adminFacade.updateStock(id, request.stockQuantity);
    }
    
    @POST
    @Path("/register")
    public Response register(AdminRegisterRequest request) {
    	adminFacade.register(request);
        return Response.ok("Admin registered successfully").build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        boolean success = adminFacade.login(request);

        if (success) {
            return Response.ok(new AdminLoginResponse("Login successful", "ADMIN")).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new AdminLoginResponse("Invalid email or password", null))
                .build();
    }
}