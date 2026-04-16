package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Item extends PanacheEntity {

    public String title;

    public String manufacturer;

    @Enumerated(EnumType.STRING)
    public ItemType itemType;

    public String category;

    public double price;

    public String description;

    public int stockQuantity;
    
    public String pictureLink;

    // empty constructor 
    public Item() {}

    // Constructor for easy creation
    public Item(String title, String manufacturer, String category,
                   double price, String description, int stockQuantity, String pictureLink) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.category = category;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.pictureLink = pictureLink;
    }
}
