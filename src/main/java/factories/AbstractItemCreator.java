package factories;

import dtos.ItemRequest;
import model.Item;

public abstract class AbstractItemCreator implements ItemCreator {

    @Override
    public Item create(ItemRequest request) {
        Item item = new Item();
        item.itemType = getSupportedType();
        item.title = request.title;
        item.manufacturer = request.manufacturer;
        item.category = resolveCategory(request);
        item.price = request.price;
        item.description = request.description;
        item.stockQuantity = request.stockQuantity;
        item.pictureLink = request.pictureLink;
        return item;
    }

    protected String resolveCategory(ItemRequest request) {
        if (request.category != null && !request.category.isBlank()) {
            return request.category;
        }

        return defaultCategory();
    }

    protected String defaultCategory() {
        return getSupportedType().name();
    }
}
