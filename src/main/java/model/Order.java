package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {

    @ManyToOne
    public Customer customer;

    public LocalDateTime orderDate;

    public double orderTotal;
    
    @Enumerated(EnumType.STRING)
    public PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderItem> orderItems = new ArrayList<>();
    public double discountAmount;
    public double subtotal;


	public Order(Customer customer, LocalDateTime orderDate, double orderTotal, List<OrderItem> orderItems, PaymentMethod paymentMethod, double discountAmount, double subtotal) {
		super();
		this.customer = customer;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
		this.orderItems = orderItems;
		this.paymentMethod = paymentMethod;
		this.discountAmount = discountAmount;
		this.subtotal = subtotal;
		


	}
    
    public Order() {
    	
    }
}


