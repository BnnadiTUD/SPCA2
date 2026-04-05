package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem extends PanacheEntity {

	//each cart has many cart items
    @ManyToOne
    public Cart cart;

    //many cart items can refer to one product
    @ManyToOne
    public Item item;

    public Integer quantity;
    
	public CartItem() {

	}

	public CartItem(Cart cart, Item item, Integer quantity) {
		this.cart = cart;
		this.item = item;
		this.quantity = quantity;
	}
    
    
}