package entities.users;

public abstract class User {
    protected int userId;
    protected String email;
    protected String password;



    // Abstract base class for different types of users in the system.
    // Demo: inheritance and polymorphism, OOP.
    public User(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getUserInfo();
}
