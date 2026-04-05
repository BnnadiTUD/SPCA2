package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Cart extends PanacheEntity {

	//one customer has one cart 
    @OneToOne
    public Customer customer;
    
	public Cart() {
	}

	public Cart(Customer customer) {
		this.customer = customer;
	}
    
    
}