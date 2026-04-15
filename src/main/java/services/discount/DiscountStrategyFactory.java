package services.discount;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Customer;
import model.PaymentMethod;

@ApplicationScoped
public class DiscountStrategyFactory {

    @Inject
    NoDiscountStrategy noDiscountStrategy;

    public DiscountStrategy getStrategy(Customer customer, PaymentMethod paymentMethod, double subtotal) {
        DiscountStrategy strategy = noDiscountStrategy;

        if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
            strategy = new CashOnDeliveryDiscountDecorator(strategy);
        }

        long orderCount = customer == null ? 0 : model.Order.count("customer.id", customer.id);
        if (orderCount >= 2) {
            strategy = new LoyaltyDiscountDecorator(strategy);
        }

        if (subtotal >= 200.0) {
            strategy = new BulkOrderDiscountDecorator(strategy);
        }

        return strategy;
    }
}
