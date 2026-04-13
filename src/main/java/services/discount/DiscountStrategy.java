package services.discount;

public interface DiscountStrategy {
    double calculateDiscount(double subtotal);
}