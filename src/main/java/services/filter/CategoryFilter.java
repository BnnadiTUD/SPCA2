package services.filter;

import model.Item;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements ItemFilter {

    private final String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public List<Item> apply(List<Item> items) {
        return items.stream()
                .filter(item -> item.category != null &&
                        item.category.toLowerCase().contains(category.toLowerCase()))
                .collect(Collectors.toList());
    }
}