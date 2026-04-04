package factories;

import dtos.ItemRequest;
import jakarta.enterprise.context.ApplicationScoped;
import model.Item;

@ApplicationScoped
public class ItemFactory {

    //createa a brand new item from the request data
    public Item createItem(ItemRequest request) {
        Item item = new Item();
        item.title = request.title;
        item.manufacturer = request.manufacturer;
        item.category = request.category;
        item.price = request.price;
        item.description = request.description;
        item.stockQuantity = request.stockQuantity;
        return item;
    }

    //copies updated values into an existing item
    public void updateItem(Item item, ItemRequest request) {
        item.title = request.title;
        item.manufacturer = request.manufacturer;
        item.category = request.category;
        item.price = request.price;
        item.description = request.description;
        item.stockQuantity = request.stockQuantity;
    }
}