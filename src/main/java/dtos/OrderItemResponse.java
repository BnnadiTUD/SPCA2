package dtos;

public class OrderItemResponse {
    public Long id;
    public String itemTitle;
    public int quantity;
    public double priceForOne;
    public double total;

    public OrderItemResponse() {}

    public OrderItemResponse(Long id, String itemTitle, int quantity, double priceForOne, double total) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.quantity = quantity;
        this.priceForOne = priceForOne;
        this.total = total;
    }
}