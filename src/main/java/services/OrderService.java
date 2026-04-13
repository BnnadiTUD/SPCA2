package services;

import java.time.LocalDateTime;
import java.util.List;

import dtos.OrderItemResponse;
import dtos.OrderResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import model.Cart;
import model.CartItem;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderItem;
import model.PaymentMethod;
import services.discount.DiscountStrategy;
import services.discount.DiscountStrategyFactory;
import services.observer.StockEvent;
import services.observer.StockEventType;
import services.observer.StockInventManager;

@ApplicationScoped
public class OrderService {

    @Inject
    StockInventManager inventoryManager;

    @Inject
    DiscountStrategyFactory discountStrategyFactory;

    @Transactional
    public Order checkout(Long customerId) {
        Customer customer = Customer.findById(customerId);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        Cart cart = Cart.find("customer.id", customerId).firstResult();
        if (cart == null) {
            throw new BadRequestException("Cart not found");
        }

        List<CartItem> cartItems = CartItem.find("cart", cart).list();
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.customer = customer;
        order.orderDate = LocalDateTime.now();
        order.paymentMethod = customer.preferredPaymentMethod;
        order.subtotal = 0.0;
        order.discountAmount = 0.0;
        order.orderTotal = 0.0;
        order.persist();

        double subtotal = 0.0;

        for (CartItem cartItem : cartItems) {
            Item item = cartItem.item;
            int oldStock = item.stockQuantity;

            if (item.stockQuantity < cartItem.quantity) {
                throw new BadRequestException("Not enough stock for item: " + item.title);
            }

            item.stockQuantity -= cartItem.quantity;

            inventoryManager.notifyObservers(new StockEvent(
                    item.id,
                    item.title,
                    oldStock,
                    item.stockQuantity,
                    StockEventType.STOCK_REDUCED_BY_ORDER
            ));

            OrderItem orderItem = new OrderItem();
            orderItem.order = order;
            orderItem.item = item;
            orderItem.quantity = cartItem.quantity;
            orderItem.priceForOne = item.price;
            orderItem.total = item.price * cartItem.quantity;
            orderItem.persist();

            subtotal += orderItem.total;
        }

        PaymentMethod paymentMethod = customer.preferredPaymentMethod;
        DiscountStrategy discountStrategy = discountStrategyFactory.getStrategy(paymentMethod);
        double discountAmount = discountStrategy.calculateDiscount(subtotal);
        double finalTotal = subtotal - discountAmount;

        order.subtotal = subtotal;
        order.discountAmount = discountAmount;
        order.orderTotal = finalTotal;
        order.paymentMethod = paymentMethod;

        CartItem.delete("cart", cart);

        return order;
    }

    public List<OrderResponse> getCustomerOrders(Long customerId) {
        List<Order> orders = Order.find("customer.id", customerId).list();

        return orders.stream()
                .map(order -> new OrderResponse(
                        order.id,
                        order.orderDate,
                        order.subtotal,
                        order.discountAmount,
                        order.orderTotal,
                        order.paymentMethod
                ))
                .toList();
    }

    public List<OrderItemResponse> getOrderItems(Long orderId) {
        List<OrderItem> items = OrderItem.find("order.id", orderId).list();

        return items.stream()
                .map(item -> new OrderItemResponse(
                        item.id,
                        item.item.title,
                        item.quantity,
                        item.priceForOne,
                        item.total
                ))
                .toList();
    }
}
