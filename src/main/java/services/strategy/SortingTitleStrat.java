package services.strategy;

import jakarta.enterprise.context.ApplicationScoped;
import model.Item;
import java.util.List;

@ApplicationScoped
public class SortingTitleStrat implements ItemSortStrategy {

    @Override
    public String getSortKey() {
        return "title";
    }

    @Override
    public void sort(List<Item> items) {
        items.sort((a, b) -> a.title.compareToIgnoreCase(b.title));
    }
}
