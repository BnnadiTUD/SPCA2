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

    public double priceAtpurchasePricePurchase;

    public double orderTotal;

	public OrderItem(Order order, Item item, int quantity, double priceAtpurchasePricePurchase, double orderTotal) {
		super();
		this.order = order;
		this.item = item;
		this.quantity = quantity;
		this.priceAtpurchasePricePurchase = priceAtpurchasePricePurchase;
		this.orderTotal = orderTotal;
	}
    
    public OrderItem() {
    	
    }
}