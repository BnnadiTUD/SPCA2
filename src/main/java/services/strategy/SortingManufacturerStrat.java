package services.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import model.Item;
import java.util.List;

@ApplicationScoped
public class SortingManufacturerStrat implements ItemSortStrategy {

    @Override
    public String getSortKey() {
        return "manufacturer";
    }

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> a.manufacturer.compareToIgnoreCase(b.manufacturer));
    }
}
