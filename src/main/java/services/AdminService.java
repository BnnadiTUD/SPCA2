package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
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

    public boolean login(LoginRequest request) {

        Admin admin = Admin.find("email", request.email).firstResult();

        if (admin == null) {
            return false;
        }

        
        return admin.passwordHash.equals(request.password);
    }
    
    @Transactional
    public void register(AdminRegisterRequest request) {

        // Check if email already exists
        Admin existing = Admin.find("email", request.email).firstResult();
        if (existing != null) {
            throw new RuntimeException("Admin already exists");
        }

        Admin admin = new Admin(
                request.name,
                request.email,
                request.password // simple version
        );

        admin.persist();
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
}