package factories;

import dtos.ItemRequest;
import jakarta.enterprise.context.ApplicationScoped;
import model.Item;

@ApplicationScoped
public class ItemFactory {

    //createa a brand new item from the request data
    public Item createItem(ItemRequest req) {
        Item item = new Item();
        item.title = req.title;
        item.manufacturer = req.manufacturer;
        item.category = req.category;
        item.price = req.price;
        item.description = req.description;
        item.stockQuantity = req.stockQuantity;
        item.pictureLink = req.pictureLink;
        return item;
    }

    //copies updated values into an existing item
    public void updateItem(Item item, ItemRequest req) {
        item.title = req.title;
        item.manufacturer = req.manufacturer;
        item.category = req.category;
        item.price = req.price;
        item.description = req.description;
        item.stockQuantity = req.stockQuantity;
        item.pictureLink = req.pictureLink;
    }
}