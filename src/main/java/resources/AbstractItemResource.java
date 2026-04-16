package resources;

import java.util.List;

import dtos.ItemResponse;
import jakarta.inject.Inject;

import services.AdminFacade;

public abstract class AbstractItemResource {

    @Inject
    AdminFacade adminFacade;

    // Template method for getting all items
    public final List<ItemResponse> getAllItemsTemplate(String sortBy, String sortDirection) {
        beforeItemAccess();
        validateSortField(sortBy);
        validateSortDirection(sortDirection);
        return adminFacade.getAllItems(sortBy, sortDirection);
    }

    // Template method for getting one item
    public final ItemResponse getItemByIdTemplate(Long id) {
        beforeItemAccess();
        validateId(id);
        return adminFacade.getItemById(id);
    }

    // Template method for searching items
    public final List<ItemResponse> searchItemsTemplate(
            String title,
            String manufacturer,
            String category,
            String itemType,
            String sortBy,
            String sortDirection) {

        beforeItemAccess();
        validateSortField(sortBy);
        validateSortDirection(sortDirection);
        return adminFacade.searchItems(title, manufacturer, category, itemType, sortBy, sortDirection);
    }

    protected abstract void beforeItemAccess();
    protected abstract boolean canUseSortField(String sortBy);

    // Shared validation steps
    protected void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid item id");
        }
    }

    protected void validateSortDirection(String sortDirection) {
        if (sortDirection != null &&
            !sortDirection.equalsIgnoreCase("asc") &&
            !sortDirection.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("sortDirection must be 'asc' or 'desc'");
        }
    }

    protected void validateSortField(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return;
        }

        if (!canUseSortField(sortBy)) {
            throw new IllegalArgumentException("sortBy value is not allowed for this resource");
        }
    }
}
