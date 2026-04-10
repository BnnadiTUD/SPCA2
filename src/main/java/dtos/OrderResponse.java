package dtos;

import java.time.LocalDateTime;
import model.PaymentMethod;

public class OrderResponse {
    public Long id;
    public LocalDateTime orderDate;
    public double orderTotal;
    public PaymentMethod paymentMethod;

    public OrderResponse() {}

    public OrderResponse(Long id, LocalDateTime orderDate, double orderTotal, PaymentMethod paymentMethod) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.paymentMethod = paymentMethod;
    }
}