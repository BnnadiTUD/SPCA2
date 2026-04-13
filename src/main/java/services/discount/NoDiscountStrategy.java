package services.discount;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(double subtotal) {
        return 0.0;
    }
}
