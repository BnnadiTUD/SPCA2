package services.filter;

import model.Item;
import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerFilter implements ItemFilter {

    private final String manufacturer;

    public ManufacturerFilter(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public List<Item> apply(List<Item> items) {
        return items.stream()
                .filter(item -> item.manufacturer != null &&
                        item.manufacturer.toLowerCase().contains(manufacturer.toLowerCase()))
                .collect(Collectors.toList());
    }
}