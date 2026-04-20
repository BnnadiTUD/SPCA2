package dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import model.ItemType;

public class ItemRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 120, message = "Title must be 120 characters or fewer")
    public String title;

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 120, message = "Manufacturer must be 120 characters or fewer")
    public String manufacturer;

    @NotNull(message = "Item family is required")
    public ItemType itemType;

    @NotBlank(message = "Category is required")
    @Size(max = 80, message = "Category must be 80 characters or fewer")
    public String category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    public Double price;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be 1000 characters or fewer")
    public String description;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    public Integer stockQuantity;
    
    @Size(max = 500, message = "Image path must be 500 characters or fewer")
    public String pictureLink;

}
