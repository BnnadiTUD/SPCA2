package dtos;

public class AdminLoginResponse {
    public String message;
    public String role;
    public Long id;
    public String name;
    public String email;

    public AdminLoginResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }

    public AdminLoginResponse(String message, String role, Long id, String name, String email) {
        this.message = message;
        this.role = role;
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
