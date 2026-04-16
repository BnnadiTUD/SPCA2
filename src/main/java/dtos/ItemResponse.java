package dtos;
import model.Item;
public class ItemResponse {

    public Long id;
    public String title;
    public String manufacturer;
    public String itemType;
    public String category;
    public double price;
    public String description;
    public int stockQuantity;
    
    public String pictureLink;
    public double averageRating;
    public long reviewCount;


    public static ItemResponse fromEntity(Item item) {
        ItemResponse response = new ItemResponse();
        response.id = item.id;
        response.title = item.title;
        response.manufacturer = item.manufacturer;
        response.itemType = item.itemType != null ? item.itemType.name() : null;
        response.category = item.category;
        response.price = item.price;
        response.description = item.description;
        response.stockQuantity = item.stockQuantity;
response.pictureLink = item.pictureLink;
        return response;
    }
}
