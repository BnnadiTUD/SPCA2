package dtos;

import model.PaymentMethod;

public class CustomerLoginResponse {
    public String message;
    public String role;
    public Long id;
    public String name;
    public String email;
    public PaymentMethod preferredPaymentMethod;

    public CustomerLoginResponse() {}

    public CustomerLoginResponse(String message, String role, Long id, String name, String email, PaymentMethod preferredPaymentMethod) {
        this.message = message;
        this.role = role;
        this.id = id;
        this.name = name;
        this.email = email;
        this.preferredPaymentMethod = preferredPaymentMethod;
    }
}