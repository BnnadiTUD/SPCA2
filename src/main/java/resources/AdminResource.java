package resources;

import dtos.LoginRequest;
import dtos.OrderResponse;
import dtos.AdminLoginResponse;
import dtos.AdminRegisterRequest;
import dtos.CustomerResponse;
import dtos.ItemRequest;
import dtos.ItemResponse;
import dtos.StockUpdateRequest;
import services.AdminFacade;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.observer.StockEvent;
import services.observer.StockInventManager;

import java.util.List;
import java.util.Set;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource extends AbstractItemResource {
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("title", "manufacturer", "category", "price", "stockQuantity");

	@Inject
	AdminFacade adminFacade;

    @Inject
    StockInventManager stockInventManager;

    @Override
    protected void beforeItemAccess() {
        // Admin-specific hook
        System.out.println("Admin item access");
    }

    @Override
    protected boolean canUseSortField(String sortBy) {
        return ALLOWED_SORT_FIELDS.contains(sortBy.toLowerCase());
    }

    @GET
    @Path("/items")
    public List<ItemResponse> getAllItems(
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortDirection") String sortDirection) {
        return getAllItemsTemplate(sortBy, sortDirection);
    }

    @GET
    @Path("/items/{id}")
    public ItemResponse getItemById(@PathParam("id") Long id) {
        return getItemByIdTemplate(id);
    }

    @GET
    @Path("/items/search")
    public List<ItemResponse> searchItems(
            @QueryParam("title") String title,
            @QueryParam("manufacturer") String manufacturer,
            @QueryParam("category") String category,
            @QueryParam("itemType") String itemType,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortDirection") String sortDirection) {
        return searchItemsTemplate(title, manufacturer, category, itemType, sortBy, sortDirection);
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
    
    @GET
    @Path("/customers")
    public List<CustomerResponse> getAllCustomers() {
        return adminFacade.getAllCustomers();
    }

    @GET
    @Path("/customers/{id}/orders")
    public List<OrderResponse> getCustomerOrders(@PathParam("id") Long id) {
        return adminFacade.getCustomerOrders(id);
    }

    @GET
    @Path("/inventory/events")
    public List<StockEvent> getRecentInventoryEvents() {
        return stockInventManager.getRecentEvents();
    }

    @GET
    @Path("/inventory/alerts")
    public List<StockEvent> getLowStockAlerts() {
        return stockInventManager.getLowStockAlerts();
    }
}
