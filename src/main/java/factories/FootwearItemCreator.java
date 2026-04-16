package factories;

import jakarta.enterprise.context.ApplicationScoped;
import model.ItemType;

@ApplicationScoped
public class FootwearItemCreator extends AbstractItemCreator {

    @Override
    public ItemType getSupportedType() {
        return ItemType.FOOTWEAR;
    }

    @Override
    protected String defaultCategory() {
        return "Footwear";
    }
}
