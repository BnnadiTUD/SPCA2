package resources;

import java.util.List;

import dtos.CustomerLoginResponse;
import dtos.CustomerRegRequest;
import dtos.ItemResponse;
import dtos.LoginRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.CartItem;
import model.Customer;
import model.Order;
import model.OrderItem;
import services.AdminFacade;
import services.CustomerService;
import services.OrderService;
import services.CartService;


@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CartService cartService;
    @Inject
    CustomerService service;
    @Inject
    AdminFacade adminFacade;
    @Inject
    OrderService orderService;
    @POST
    @Path("/register")
    public Response register(CustomerRegRequest req) {
        Customer c = service.register(req);

        CustomerLoginResponse response = new CustomerLoginResponse(
            "Customer registered successfully",
            "CUSTOMER",
            c.id,
            c.name,
            c.email
        );

        return Response.ok(response).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        CustomerLoginResponse response = service.login(req.email, req.password);
        return Response.ok(response).build();
    }
    
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
    @Path("/cart/add")
    public Response addToCart(
            @QueryParam("customerId") Long customerId,
            @QueryParam("itemId") Long itemId,
            @QueryParam("quantity") int quantity) {

        cartService.addToCart(customerId, itemId, quantity);
        return Response.ok("Item added to cart").build();
    }
    
    @GET
    @Path("/cart")
    public List<CartItem> getCart(@QueryParam("customerId") Long customerId) {
        return cartService.getCartItems(customerId);
    }
    
    @DELETE
    @Path("/cart/remove")
    public Response removeFromCart(@QueryParam("cartItemId") Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return Response.ok("Item removed from cart").build();
    }
    
    @POST
    @Path("/orders/checkout")
    public Response checkout(@QueryParam("customerId") Long customerId) {
        Order order = orderService.checkout(customerId);
        return Response.ok(order).build();
    }

    @GET
    @Path("/orders")
    public List<Order> getOrders(@QueryParam("customerId") Long customerId) {
        return orderService.getCustomerOrders(customerId);
    }

    @GET
    @Path("/orders/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathParam("orderId") Long orderId) {
        return orderService.getOrderItems(orderId);
    }
}
