package dtos;

public class AdminLoginResponse {
    public String message;
    public String role;

    public AdminLoginResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }
}