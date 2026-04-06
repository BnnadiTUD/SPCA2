package dtos;

import java.time.LocalDateTime;

public class OrderResponse {
    public Long id;
    public LocalDateTime orderDate;
    public double orderTotal;

    public OrderResponse() {}

    public OrderResponse(Long id, LocalDateTime orderDate, double orderTotal) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
    }
}