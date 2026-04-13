package services.discount;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.PaymentMethod;

@ApplicationScoped
public class DiscountStrategyFactory {

    @Inject
    CODDiscountStrategy cashOnDeliveryDiscountStrategy;

    @Inject
    NoDiscountStrategy noDiscountStrategy;

    public DiscountStrategy getStrategy(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return noDiscountStrategy;
        }

        return switch (paymentMethod) {
            case CASH_ON_DELIVERY -> cashOnDeliveryDiscountStrategy;
            case CARD, PAYPAL -> noDiscountStrategy;
        };
    }
}