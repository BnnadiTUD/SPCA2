package services.strategy;

import model.Item;
import java.util.List;

public interface ItemSortStrategy {
    String getSortKey();
    void sort(List<Item> items);
}
