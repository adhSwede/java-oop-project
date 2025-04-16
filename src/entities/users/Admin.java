package entities.users;

// Admin and Customer both extend User abstract superclass
public class Admin extends User {
    private String username;

    public Admin(int adminId, String username, String password) {
        super(adminId, "admin@" + username + ".com", password); // placeholder email
        this.username = username;
    }

    public Admin(String username, String password) {
        super(-1, "admin@" + username + ".com", password); // placeholder id & email
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Polymorph overrides from User superclass
    @Override
    public String getUserInfo() {
        return "Admin: " + username + " (ID: " + getUserId() + ")";
    }

    @Override
    public String toString() {
        return "Admin ID: " + getUserId() + ", Username: " + username;
    }
}