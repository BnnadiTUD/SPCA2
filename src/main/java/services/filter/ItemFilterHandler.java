package services.filter;

import model.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemFilterHandler {

    private final List<ItemFilter> filters = new ArrayList<>();

    public void addFilter(ItemFilter filter) {
        filters.add(filter);
    }

    public List<Item> applyFilters(List<Item> items) {
        List<Item> filteredItems = items;

        for (ItemFilter filter : filters) {
            filteredItems = filter.apply(filteredItems);
        }

        return filteredItems;
    }
}
