package repos;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.Customer;

@ApplicationScoped
public class CustomerRepo implements PanacheRepository<Customer> {
}