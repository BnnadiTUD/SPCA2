package resources;

import dtos.AdminLoginRequest;
import dtos.AdminLoginResponse;
import dtos.AdminRegisterRequest;
import dtos.ItemRequest;
import dtos.ItemResponse;
import dtos.StockUpdateRequest;
import services.AdminService;
import services.ItemService;
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
    ItemService itemService;
    @Inject
    AdminService adminService;

    @GET
    @Path("/items")
    public List<ItemResponse> getAllItems(
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortDirection") String sortDirection) {
        return itemService.getAllItems(sortBy, sortDirection);
    }

    @GET
    @Path("/items/{id}")
    public ItemResponse getItemById(@PathParam("id") Long id) {
        return itemService.getItemById(id);
    }

    @GET
    @Path("/items/search")
    public List<ItemResponse> searchItems(
            @QueryParam("title") String title,
            @QueryParam("manufacturer") String manufacturer,
            @QueryParam("category") String category,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortDirection") String sortDirection) {
        return itemService.searchItems(title, manufacturer, category, sortBy, sortDirection);
    }

    @POST
    @Path("/items")
    public Response createItem(@Valid ItemRequest request) {
        ItemResponse createdItem = itemService.createItem(request);
        return Response.status(Response.Status.CREATED).entity(createdItem).build();
    }

    @PUT
    @Path("/items/{id}")
    public ItemResponse updateItem(@PathParam("id") Long id, @Valid ItemRequest request) {
        return itemService.updateItem(id, request);
    }

    @DELETE
    @Path("/items/{id}")
    public Response deleteItem(@PathParam("id") Long id) {
        itemService.deleteItem(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/items/{id}/stock")
    public ItemResponse updateStock(@PathParam("id") Long id, @Valid StockUpdateRequest request) {
        return itemService.updateStock(id, request.stockQuantity);
    }
    
    @POST
    @Path("/register")
    public Response register(AdminRegisterRequest request) {
        adminService.register(request);
        return Response.ok("Admin registered successfully").build();
    }

    @POST
    @Path("/login")
    public Response login(AdminLoginRequest request) {
        boolean success = adminService.login(request);

        if (success) {
            return Response.ok(new AdminLoginResponse("Login successful", "ADMIN")).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new AdminLoginResponse("Invalid email or password", null))
                .build();
    }
}