package services.filter;

import model.Item;
import java.util.List;

public interface ItemFilter {
    List<Item> apply(List<Item> items);
}