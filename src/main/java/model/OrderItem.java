package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem extends PanacheEntity {

    @ManyToOne
    public Order order;

    @ManyToOne
    public Item item;

    public int quantity;

    public double priceForOne;

    public double total;

	public OrderItem(Order order, Item item, int quantity, double priceForOne, double total) {
		super();
		this.order = order;
		this.item = item;
		this.quantity = quantity;
		this.priceForOne = priceForOne;
		this.total = total;
	}
    
    public OrderItem() {
    	
    }
}