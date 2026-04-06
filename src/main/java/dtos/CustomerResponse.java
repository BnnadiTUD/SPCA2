package dtos;


public class CustomerResponse {
    public Long id;
    public String name;
    public String email;
    public String address;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
