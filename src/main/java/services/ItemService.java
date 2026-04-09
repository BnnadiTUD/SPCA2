package services;
import services.observer.StockInventManager;
import services.observer.StockEvent;
import services.observer.StockEventType;
import dtos.ItemRequest;
import dtos.ItemResponse;
import factories.ItemFactory;
import model.Item;
import repos.ItemRepo;
import services.filter.CategoryFilter;
import services.filter.ItemFilterHandler;
import services.filter.ManufacturerFilter;
import services.filter.TitleFilter;
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
    StockInventManager inventoryManager;

    @Inject
    ItemSortStrategyFactory sortStrategyFactory;

    @Inject
    ItemFactory itemFactory;

    public List<ItemResponse> getAllItems(String sortBy, String sortDirection) {
        List<Item> items = iRepository.findAllItems();

        sortItems(items, sortBy, sortDirection);

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

	List<Item> items = iRepository.findAllItems();
	
	ItemFilterHandler filterHandler = new ItemFilterHandler();
	
	if (title != null && !title.isBlank()) {
	filterHandler.addFilter(new TitleFilter(title));
	}
	
	if (manufacturer != null && !manufacturer.isBlank()) {
	filterHandler.addFilter(new ManufacturerFilter(manufacturer));
	}
	
	if (category != null && !category.isBlank()) {
	filterHandler.addFilter(new CategoryFilter(category));
	}
	
	items = filterHandler.applyFilters(items);
	
	sortItems(items, sortBy, sortDirection);
	
	List<ItemResponse> responses = new ArrayList<>();
	for (Item item : items) {
	responses.add(ItemResponse.fromEntity(item));
	}
	
	return responses;
	}

    @Transactional
    public ItemResponse createItem(ItemRequest request) {
        Item item = itemFactory.createItem(request);
        iRepository.saveItem(item);

        inventoryManager.notifyObservers(new StockEvent(
                item.id,
                item.title,
                0,
                item.stockQuantity,
                StockEventType.ITEM_CREATED
        ));

        return ItemResponse.fromEntity(item);
    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item item = iRepository.findItemById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        itemFactory.updateItem(item, request);

        return ItemResponse.fromEntity(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        Item item = iRepository.findItemById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        int oldStock = item.stockQuantity;
        boolean deleted = iRepository.deleteItemById(id);

        if (!deleted) {
            throw new NotFoundException("Item not found with id: " + id);
        }

        inventoryManager.notifyObservers(new StockEvent(
                id,
                item.title,
                oldStock,
                0,
                StockEventType.ITEM_DELETED
        ));
    }

    @Transactional
    public ItemResponse updateStock(Long id, int newStockQuantity) {
        Item item = iRepository.findItemById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        int oldStock = item.stockQuantity;
        item.stockQuantity = newStockQuantity;

        inventoryManager.notifyObservers(new StockEvent(
                item.id,
                item.title,
                oldStock,
                newStockQuantity,
                StockEventType.STOCK_UPDATED
        ));

        return ItemResponse.fromEntity(item);
    }

    // I use the selected strategy to sort the list in memory
    private void sortItems(List<Item> items, String sortBy, String sortDirection) {
        ItemSortStrategy strategy = sortStrategyFactory.getStrategy(sortBy);

        if (strategy == null) {
            return;
        }

        strategy.sort(items);

        if ("desc".equalsIgnoreCase(sortDirection)) {
            Collections.reverse(items);
        }
    }
}