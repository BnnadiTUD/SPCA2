package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Review extends PanacheEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    public Item item;

    public int rating;
    public String comment;
    public LocalDateTime createdAt;

    public Review() {
    }

	public Review(Customer customer, Item item, int rating, String comment, LocalDateTime createdAt) {
		super();
		this.customer = customer;
		this.item = item;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
	}
    
    
}