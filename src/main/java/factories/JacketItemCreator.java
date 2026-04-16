package factories;

import jakarta.enterprise.context.ApplicationScoped;
import model.ItemType;

@ApplicationScoped
public class JacketItemCreator extends AbstractItemCreator {

    @Override
    public ItemType getSupportedType() {
        return ItemType.JACKET;
    }

    @Override
    protected String defaultCategory() {
        return "Jackets";
    }
}
