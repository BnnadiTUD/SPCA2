package services.strategy;

import model.Item;
import java.util.List;

public class SortingStockStrat implements ItemSortStrategy {

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> Integer.compare(a.stockQuantity, b.stockQuantity));
    }
}