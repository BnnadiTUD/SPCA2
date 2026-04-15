package services.discount;

public class LoyaltyDiscountDecorator extends AbstractDiscountDecorator {

    private static final double DISCOUNT_RATE = 0.03;

    public LoyaltyDiscountDecorator(DiscountStrategy wrapped) {
        super(wrapped);
    }

    @Override
    protected double calculateOwnDiscount(double subtotal) {
        return subtotal * DISCOUNT_RATE;
    }
}
