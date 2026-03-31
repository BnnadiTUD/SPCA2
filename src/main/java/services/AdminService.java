package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.Admin;
import dtos.AdminLoginRequest;
import dtos.AdminRegisterRequest;

@ApplicationScoped
public class AdminService {

    public boolean login(AdminLoginRequest request) {

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
                request.password // simple version (no hashing yet)
        );

        admin.persist();
    }
}