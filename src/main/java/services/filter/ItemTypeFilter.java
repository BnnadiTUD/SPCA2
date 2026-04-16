package services.filter;

import java.util.List;
import java.util.stream.Collectors;

import model.Item;

public class ItemTypeFilter implements ItemFilter {

    private final String itemType;

    public ItemTypeFilter(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public List<Item> apply(List<Item> items) {
        return items.stream()
                .filter(item -> item.itemType != null
                        && item.itemType.name().equalsIgnoreCase(itemType))
                .collect(Collectors.toList());
    }
}
