package resources;

import java.util.List;
import java.util.Set;

import dtos.CustomerLoginResponse;
import dtos.CustomerRegRequest;
import dtos.ItemResponse;
import dtos.LoginRequest;
import dtos.OrderItemResponse;
import dtos.OrderResponse;
import dtos.ReviewRequest;
import dtos.ReviewResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.CartItem;
import model.Customer;

import services.CustomerService;
import services.OrderService;
import services.ReviewService;
import services.CartService;


@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource extends AbstractItemResource {
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("title", "manufacturer", "category", "price");

    @Inject
    CartService cartService;
    @Inject
    CustomerService service;

    @Inject
    OrderService orderService;
    @Inject
    ReviewService rs;
    @POST
    @Path("/register")
    public Response register(CustomerRegRequest req) {
        Customer c = service.register(req);

        CustomerLoginResponse response = new CustomerLoginResponse(
            "Customer registered successfully",
            "CUSTOMER",
            c.id,
            c.name,
            c.email,
            c.preferredPaymentMethod
        );

        return Response.ok(response).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        CustomerLoginResponse response = service.login(req.email, req.password);
        return Response.ok(response).build();
    }
    
    @Override
    protected void beforeItemAccess() {
        // Customer-specific hook
        System.out.println("Customer item access");
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
        orderService.checkout(customerId);
        return Response.ok("Checkout successful").build();
    }
    @GET
    @Path("/orders")
    public List<OrderResponse> getOrders(@QueryParam("customerId") Long customerId) {
        return orderService.getCustomerOrders(customerId);
    }

    @GET
    @Path("/orders/{orderId}/items")
    public List<OrderItemResponse> getOrderItems(@PathParam("orderId") Long orderId) {
        return orderService.getOrderItems(orderId);
    }
    
    @POST
    @Path("/items/{itemId}/reviews")
    public ReviewResponse addReview(@PathParam("itemId") Long itemId, ReviewRequest request) {
        return rs.addReview(itemId, request);
    }

    @GET
    @Path("/items/{itemId}/reviews")
    public List<ReviewResponse> getReviewsForItem(@PathParam("itemId") Long itemId) {
        return rs.getReviewsForItem(itemId);
    }
}
