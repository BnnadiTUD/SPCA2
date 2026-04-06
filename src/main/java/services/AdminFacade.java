package services;

import java.util.List;

import dtos.AdminRegisterRequest;
import dtos.CustomerResponse;
import dtos.ItemRequest;
import dtos.ItemResponse;
import dtos.LoginRequest;
import dtos.OrderResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AdminFacade {

    @Inject
    AdminService adminService;

    @Inject
    ItemService itemService;

    //  login
    public boolean login(LoginRequest request) {
        return adminService.login(request);
    }

    // register
    public void register(AdminRegisterRequest request) {
        adminService.register(request);
    }

    // Create item
    public ItemResponse createItem(ItemRequest request) {
        return itemService.createItem(request);
    }

    // Update item
    public ItemResponse updateItem(Long id, ItemRequest request) {
        return itemService.updateItem(id, request);
    }

    // Delete item
    public void deleteItem(Long id) {
        itemService.deleteItem(id);
    }

    // Get all items without sorting
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }

    // Get all items with sorting
    public List<ItemResponse> getAllItems(String sortBy, String sortDirection) {
        return itemService.getAllItems(sortBy, sortDirection);
    }

    // Get one item by id
    public ItemResponse getItemById(Long id) {
        return itemService.getItemById(id);
    }

    // Search items
    public List<ItemResponse> searchItems(String title, String manufacturer, String category, String sortBy, String sortDirection) 
    {
        return itemService.searchItems(title, manufacturer, category, sortBy, sortDirection);
    }

    // Update stock only
    public ItemResponse updateStock(Long id, Integer stockQuantity) {
        return itemService.updateStock(id, stockQuantity);
    }
    
    public List<CustomerResponse> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    public List<OrderResponse> getCustomerOrders(Long customerId) {
        return adminService.getCustomerOrders(customerId);
    }
}