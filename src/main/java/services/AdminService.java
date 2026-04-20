package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import model.Admin;

import java.util.List;

import dtos.AdminRegisterRequest;
import dtos.LoginRequest;
import dtos.CustomerResponse;
import dtos.OrderResponse;
import model.Customer;
import model.Order;

@ApplicationScoped
public class AdminService {

    @Transactional
    public Admin login(LoginRequest request) {

        Admin admin = Admin.find("email", normalizeEmail(request.email)).firstResult();

        if (admin == null) {
            return null;
        }

        if (!admin.passwordHash.equals(request.password)) {
            return null;
        }

        return admin;
    }
    
    @Transactional
    public Admin register(AdminRegisterRequest request) {

        // Check if email already exists
        String normalizedEmail = normalizeEmail(request.email);

        Admin existing = Admin.find("email", normalizedEmail).firstResult();
        if (existing != null) {
            throw new BadRequestException("An admin with that email already exists");
        }

        Admin admin = new Admin(
                request.name,
                normalizedEmail,
                request.password
        );

        admin.persist();
        return admin;
    }
    
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = Customer.listAll();

        return customers.stream()
            .map(customer -> new CustomerResponse(
                customer.id,
                customer.name,
                customer.email,
                customer.address,
                customer.preferredPaymentMethod
            ))
            .toList();
    }   

    public List<OrderResponse> getCustomerOrders(Long customerId) {
        List<Order> orders = Order.find("customer.id", customerId).list();

        return orders.stream()
            .map(order -> new OrderResponse(
                order.id,
                order.orderDate,
                order.subtotal,
                order.discountAmount,
                order.orderTotal,
                order.paymentMethod
              
                
            ))
            .toList();
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
