package services.discount;

public class CashOnDeliveryDiscountDecorator extends AbstractDiscountDecorator {

    private static final double DISCOUNT_RATE = 0.05;

    public CashOnDeliveryDiscountDecorator(DiscountStrategy wrapped) {
        super(wrapped);
    }

    @Override
    protected double calculateOwnDiscount(double subtotal) {
        return subtotal * DISCOUNT_RATE;
    }
}
