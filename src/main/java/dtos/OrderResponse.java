package dtos;

import java.time.LocalDateTime;
import model.PaymentMethod;

public class OrderResponse {
    public Long id;
    public LocalDateTime orderDate;
    public double subtotal;
    public double discountAmount;
    public double orderTotal;
    public PaymentMethod paymentMethod;

    public OrderResponse() {
    }

    public OrderResponse(Long id, LocalDateTime orderDate, double subtotal,
                         double discountAmount, double orderTotal, PaymentMethod paymentMethod) {
        this.id = id;
        this.orderDate = orderDate;
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.orderTotal = orderTotal;
        this.paymentMethod = paymentMethod;
    }
}