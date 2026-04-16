package factories;

import jakarta.enterprise.context.ApplicationScoped;
import model.ItemType;

@ApplicationScoped
public class ShirtItemCreator extends AbstractItemCreator {

    @Override
    public ItemType getSupportedType() {
        return ItemType.SHIRT;
    }

    @Override
    protected String defaultCategory() {
        return "Shirts";
    }
}
