package services.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import model.Item;
import java.util.List;

@ApplicationScoped
public class SortingCategoryStrat implements ItemSortStrategy {

    @Override
    public String getSortKey() {
        return "category";
    }

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> a.category.compareToIgnoreCase(b.category));
    }
}
