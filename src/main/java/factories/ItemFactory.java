package factories;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import dtos.ItemRequest;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import model.Item;
import model.ItemType;

@ApplicationScoped
public class ItemFactory {

    @Inject
    Instance<ItemCreator> availableCreators;

    private Map<ItemType, ItemCreator> creatorRegistry;

    @PostConstruct
    void init() {
        Map<ItemType, ItemCreator> registry = new LinkedHashMap<>();

        for (ItemCreator creator : availableCreators) {
            registry.put(creator.getSupportedType(), creator);
        }

        creatorRegistry = Collections.unmodifiableMap(registry);
    }

    public Item createItem(ItemRequest req) {
        ItemType itemType = req.itemType != null ? req.itemType : inferItemType(req.category);
        ItemCreator creator = creatorRegistry.get(itemType);

        if (creator == null) {
            throw new IllegalArgumentException("Unsupported item type: " + itemType);
        }

        return creator.create(req);
    }

    public void updateItem(Item item, ItemRequest req) {
        ItemType requestedType = req.itemType != null ? req.itemType : inferItemType(req.category);

        item.itemType = requestedType;
        item.title = req.title;
        item.manufacturer = req.manufacturer;
        item.category = req.category;
        item.price = req.price;
        item.description = req.description;
        item.stockQuantity = req.stockQuantity;
        item.pictureLink = req.pictureLink;

        if ((item.category == null || item.category.isBlank()) && creatorRegistry.containsKey(requestedType)) {
            item.category = creatorRegistry.get(requestedType).create(req).category;
        }
    }

    private ItemType inferItemType(String category) {
        if (category == null || category.isBlank()) {
            return ItemType.SHIRT;
        }

        String normalized = category.trim().toLowerCase();

        if (normalized.contains("jacket") || normalized.contains("coat")) {
            return ItemType.JACKET;
        }

        if (normalized.contains("shoe") || normalized.contains("boot") || normalized.contains("footwear")
                || normalized.contains("trainer") || normalized.contains("sneaker")) {
            return ItemType.FOOTWEAR;
        }

        return ItemType.SHIRT;
    }
}
