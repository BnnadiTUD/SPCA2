package dtos;

import model.PaymentMethod;

public class CustomerRegRequest {
    public String name;
    public String email;
    public String password;
    public String address;
    public PaymentMethod preferredPaymentMethod;

}
