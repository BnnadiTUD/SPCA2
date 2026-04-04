package services;

import dtos.ItemRequest;
import dtos.ItemResponse;
import model.Item;
import repos.ItemRepo;
import services.strategy.ItemSortStrategy;
import services.strategy.ItemSortStrategyFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ItemService {

    @Inject
    ItemRepo iRepository;

    @Inject
    ItemSortStrategyFactory sortStrategyFactory;

    public List<ItemResponse> getAllItems(String sortBy, String sortDirection) {
        List<Item> items;

        ItemSortStrategy strategy = sortStrategyFactory.getStrategy(sortBy);

        if (strategy != null) {
            items = iRepository.findAllSorted(strategy.getSortField(), sortDirection);
        } else {
            items = iRepository.findAllItems();
        }

        List<ItemResponse> responses = new ArrayList<>();

        for (Item item : items) {
            responses.add(ItemResponse.fromEntity(item));
        }

        return responses;
    }

    public List<ItemResponse> getAllItems() {
        return getAllItems(null, null);
    }

    public ItemResponse getItemById(Long id) {
        Item item = iRepository.findItemById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        return ItemResponse.fromEntity(item);
    }

    public List<ItemResponse> searchItems(String title, String manufacturer, String category,
                                          String sortBy, String sortDirection) {
        List<Item> items;

        if (title != null && !title.isBlank()) {
            items = iRepository.searchByTitle(title);
        } else if (manufacturer != null && !manufacturer.isBlank()) {
            items = iRepository.searchByManufacturer(manufacturer);
        } else if (category != null && !category.isBlank()) {
            items = iRepository.searchByCategory(category);
        } else {
            items = iRepository.findAllItems();
        }

        sortItems(items, sortBy, sortDirection);

        List<ItemResponse> responses = new ArrayList<>();

        for (Item item : items) {
            responses.add(ItemResponse.fromEntity(item));
        }

        return responses;
    }

    @Transactional
    public ItemResponse createItem(ItemRequest request) {
        Item item = new Item();
        item.title = request.title;
        item.manufacturer = request.manufacturer;
        item.category = request.category;
        item.price = request.price;
        item.description = request.description;
        item.stockQuantity = request.stockQuantity;

        iRepository.saveItem(item);

        return ItemResponse.fromEntity(item);
    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item item = iRepository.findItemById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        item.title = request.title;
        item.manufacturer = request.manufacturer;
        item.category = request.category;
        item.price = request.price;
        item.description = request.description;
        item.stockQuantity = request.stockQuantity;

        return ItemResponse.fromEntity(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        boolean deleted = iRepository.deleteItemById(id);

        if (!deleted) {
            throw new NotFoundException("Item not found with id: " + id);
        }
    }

    @Transactional
    public ItemResponse updateStock(Long id, int newStockQuantity) {
        Item item = iRepository.findItemById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        item.stockQuantity = newStockQuantity;

        return ItemResponse.fromEntity(item);
    }

    private void sortItems(List<Item> items, String sortBy, String sortDirection) {
        ItemSortStrategy strategy = sortStrategyFactory.getStrategy(sortBy);

        if (strategy == null) {
            return;
        }

        String field = strategy.getSortField();

        if ("title".equalsIgnoreCase(field)) {
            items.sort((a, b) -> a.title.compareToIgnoreCase(b.title));
        } else if ("manufacturer".equalsIgnoreCase(field)) {
            items.sort((a, b) -> a.manufacturer.compareToIgnoreCase(b.manufacturer));
        } else if ("category".equalsIgnoreCase(field)) {
            items.sort((a, b) -> a.category.compareToIgnoreCase(b.category));
        } else if ("price".equalsIgnoreCase(field)) {
            items.sort((a, b) -> Double.compare(a.price, b.price));
        } else if ("stockQuantity".equalsIgnoreCase(field)) {
            items.sort((a, b) -> Integer.compare(a.stockQuantity, b.stockQuantity));
        }

        if ("desc".equalsIgnoreCase(sortDirection)) {
            Collections.reverse(items);
        }
    }
}