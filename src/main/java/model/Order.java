package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Order extends PanacheEntity {

    @ManyToOne
    public Customer customer;

    public LocalDateTime orderDate;

    public double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderItem> orderItems = new ArrayList<>();

	public Order(Customer customer, LocalDateTime orderDate, double totalPrice, List<OrderItem> orderItems) {
		super();
		this.customer = customer;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.orderItems = orderItems;
	}
    
    public Order() {
    	
    }
}


