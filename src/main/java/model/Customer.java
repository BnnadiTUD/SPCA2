package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Customer extends PanacheEntity {

    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    public String password;
    public String address;
    
    @Enumerated(EnumType.STRING)
    public PaymentMethod preferredPaymentMethod;
    
    public Customer() {
    	
    }
    
	public Customer(String name, String email, String password, String address, PaymentMethod preferredPaymentMethod) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.preferredPaymentMethod = preferredPaymentMethod;
	}
}
