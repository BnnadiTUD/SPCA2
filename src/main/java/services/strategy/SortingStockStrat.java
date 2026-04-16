package services.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import model.Item;
import java.util.List;

@ApplicationScoped
public class SortingStockStrat implements ItemSortStrategy {

    @Override
    public String getSortKey() {
        return "stockquantity";
    }

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> Integer.compare(a.stockQuantity, b.stockQuantity));
    }
}
