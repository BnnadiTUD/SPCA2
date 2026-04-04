package services.strategy;

import model.Item;
import java.util.List;

public class SortingManufacturerStrat implements ItemSortStrategy {

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> a.manufacturer.compareToIgnoreCase(b.manufacturer));
    }
}
