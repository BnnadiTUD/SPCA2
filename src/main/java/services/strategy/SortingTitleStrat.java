package services.strategy;

import model.Item;
import java.util.List;

public class SortingTitleStrat implements ItemSortStrategy {

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> a.title.compareToIgnoreCase(b.title));
    }
}