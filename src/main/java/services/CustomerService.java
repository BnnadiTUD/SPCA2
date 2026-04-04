package services;

import dtos.CustomerRegRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import model.Customer;

//service class for customer logic not actually customer service ;)

@ApplicationScoped
public class CustomerService {

	@Transactional
    public Customer register(CustomerRegRequest req) {
        Customer c = new Customer();
        c.name = req.name;
        c.email = req.email;
        c.password = req.password;
        c.address = req.address;
        c.persist();
        return c;
    }

    public Customer login(String email, String password) {
        Customer c = Customer.find("email", email).firstResult();

        if (c == null || !c.password.equals(password)) {
            throw new NotAuthorizedException("Invalid credentials");
        }

        return c;
    }
}
