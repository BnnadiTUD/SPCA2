package services.filter;

import model.Item;
import java.util.List;
import java.util.stream.Collectors;

public class TitleFilter implements ItemFilter {

    private final String title;

    public TitleFilter(String title) {
        this.title = title;
    }

    @Override
    public List<Item> apply(List<Item> items) {
        return items.stream()
                .filter(item -> item.title != null &&
                        item.title.toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }
}