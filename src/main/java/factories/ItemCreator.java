package factories;

import dtos.ItemRequest;
import model.Item;
import model.ItemType;

public interface ItemCreator {
    ItemType getSupportedType();
    Item create(ItemRequest request);
}
