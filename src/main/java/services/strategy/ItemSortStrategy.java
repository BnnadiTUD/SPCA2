package services.strategy;

import model.Item;
import java.util.List;

public interface ItemSortStrategy {
    void sort(List<Item> items);
}