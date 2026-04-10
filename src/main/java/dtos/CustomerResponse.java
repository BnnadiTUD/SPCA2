package dtos;

import model.Customer;
import model.PaymentMethod;

public class CustomerResponse {

    public Long id;
    public String name;
    public String email;
    public String address;
    public PaymentMethod preferredPaymentMethod;

    public CustomerResponse() {}

    public CustomerResponse(Long id, String name, String email, String address, PaymentMethod preferredPaymentMethod) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public static CustomerResponse fromEntity(Customer c) {
        return new CustomerResponse(
            c.id,
            c.name,
            c.email,
            c.address,
            c.preferredPaymentMethod
        );
    }
}
