package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class ItemSearchResourceTest {

    private void seedItems() {
        createItem("Storm Jacket", "Northwind", "JACKET", "Jackets", 149.99, "Waterproof shell jacket", 6, "images/jacket.jpg");
        createItem("Oxford Shirt", "Tailor Co", "SHIRT", "Shirts", 59.99, "Cotton oxford shirt", 10, "images/shirt.jpg");
        createItem("Trail Runner", "Stride", "FOOTWEAR", "Footwear", 89.99, "Trail running shoe", 12, "images/shoe.jpg");
    }

    private void createItem(String title, String manufacturer, String itemType, String category,
            double price, String description, int stockQuantity, String pictureLink) {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "title": "%s",
                          "manufacturer": "%s",
                          "itemType": "%s",
                          "category": "%s",
                          "price": %s,
                          "description": "%s",
                          "stockQuantity": %s,
                          "pictureLink": "%s"
                        }
                        """.formatted(title, manufacturer, itemType, category, price, description, stockQuantity, pictureLink))
                .when()
                .post("/admin/items")
                .then()
                .statusCode(201);
    }

    @Test
    void searchFiltersByItemType() {
        seedItems();

        given()
                .contentType(ContentType.JSON)
                .queryParam("itemType", "JACKET")
                .when()
                .get("/customers/items/search")
                .then()
                .statusCode(200)
                .body("title", hasItem("Storm Jacket"))
                .body("itemType", hasItem("JACKET"));
    }

    @Test
    void searchCombinesItemTypeWithManufacturer() {
        seedItems();

        given()
                .contentType(ContentType.JSON)
                .queryParam("itemType", "FOOTWEAR")
                .queryParam("manufacturer", "Stride")
                .when()
                .get("/customers/items/search")
                .then()
                .statusCode(200)
                .body("title", hasItem("Trail Runner"))
                .body("itemType", hasItem("FOOTWEAR"))
                .body("manufacturer", hasItem("Stride"));
    }
}
