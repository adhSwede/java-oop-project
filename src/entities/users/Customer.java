package entities.users;

// Admin and Customer both extend User abstract superclass
public class Customer extends User {
    private String name;
    private String phone;
    private String address;

    public Customer(int customerId, String name, String email, String phone, String address, String password) {
        super(customerId, email, password);
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Customer(String name, String email, String phone, String address, String password) {
        super(-1, email, password); // placeholder
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getUserInfo() {
        return "Customer: " + name + " (ID: " + getUserId() + ")";
    }

    // Polymorph override from User superclass
    @Override
    public String toString() {
        return "Customer ID: " + getUserId() + ", Name: " + name + ", Email: " + getEmail()
                + ", Phone: " + phone + ", Address: " + address;
    }
}
