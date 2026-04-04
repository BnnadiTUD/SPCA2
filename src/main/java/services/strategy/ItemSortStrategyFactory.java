package services.strategy;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemSortStrategyFactory {

    public ItemSortStrategy getStrategy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return null;
        }

        return switch (sortBy.toLowerCase()) {
            case "title" -> new SortingTitleStrat();
            case "manufacturer" -> new SortingManufacturerStrat();
            case "category" -> new SortingCategoryStrat();
            case "price" -> new SortingPriceStrat();
            case "stockquantity" -> new SortingStockStrat();
            default -> null;
        };
    }
}