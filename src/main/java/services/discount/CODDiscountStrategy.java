package services.discount;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CODDiscountStrategy implements DiscountStrategy {

    private static final double DISCOUNT_RATE = 0.05;

    @Override
    public double calculateDiscount(double subtotal) {
        return subtotal * DISCOUNT_RATE;
    }
}