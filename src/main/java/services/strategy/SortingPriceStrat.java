package services.strategy;

import model.Item;
import java.util.List;

public class SortingPriceStrat implements ItemSortStrategy {

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> Double.compare(a.price, b.price));
    }
}