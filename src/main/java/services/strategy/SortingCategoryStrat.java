package services.strategy;

import model.Item;
import java.util.List;

public class SortingCategoryStrat implements ItemSortStrategy {

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> a.category.compareToIgnoreCase(b.category));
    }
}