package services;

import dtos.CustomerLoginResponse;
import dtos.CustomerRegRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import model.Customer;

// service class for customer logic not actually customer service ;)

@ApplicationScoped
public class CustomerService {

    @Transactional
    public Customer register(CustomerRegRequest req) {
        String normalizedEmail = normalizeEmail(req.email);

        Customer existing = Customer.find("email", normalizedEmail).firstResult();
        if (existing != null) {
            throw new BadRequestException("A customer with that email already exists");
        }

        Customer c = new Customer();
        c.name = req.name;
        c.email = normalizedEmail;
        c.password = req.password;
        c.address = req.address;
        c.preferredPaymentMethod = req.preferredPaymentMethod;
        c.persist();
        return c;
    }

    @Transactional
    public CustomerLoginResponse login(String email, String password) {
        Customer c = Customer.find("email", normalizeEmail(email)).firstResult();

        if (c == null || !c.password.equals(password)) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        return new CustomerLoginResponse(
            "Customer login successful",
            "CUSTOMER",
            c.id,
            c.name,
            c.email,
            c.preferredPaymentMethod
        );
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
