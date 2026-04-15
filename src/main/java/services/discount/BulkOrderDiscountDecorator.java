package services.discount;

public class BulkOrderDiscountDecorator extends AbstractDiscountDecorator {

    private static final double DISCOUNT_RATE = 0.02;

    public BulkOrderDiscountDecorator(DiscountStrategy wrapped) {
        super(wrapped);
    }

    @Override
    protected double calculateOwnDiscount(double subtotal) {
        return subtotal * DISCOUNT_RATE;
    }
}
