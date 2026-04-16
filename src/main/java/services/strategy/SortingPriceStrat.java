package services.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import model.Item;
import java.util.List;

@ApplicationScoped
public class SortingPriceStrat implements ItemSortStrategy {

    @Override
    public String getSortKey() {
        return "price";
    }

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> Double.compare(a.price, b.price));
    }
}
