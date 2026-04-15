package services.discount;

public abstract class AbstractDiscountDecorator implements DiscountStrategy {

    private final DiscountStrategy wrapped;

    protected AbstractDiscountDecorator(DiscountStrategy wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return wrapped.calculateDiscount(subtotal) + calculateOwnDiscount(subtotal);
    }

    protected abstract double calculateOwnDiscount(double subtotal);
}
