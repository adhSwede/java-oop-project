package entities.users;

public class Admin extends User {
    private String username;

    // #################### [ Constructors ] ####################
    public Admin(int adminId, String username, String password) {
        super(adminId, "admin@" + username + ".com", password); // placeholder email
        this.username = username;
    }

    public Admin(String username, String password) {
        super(-1, "admin@" + username + ".com", password); // placeholder ID & email
        this.username = username;
    }

    // #################### [ Getters & Setters ] ####################
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // #################### [ Overrides ] ####################
    @Override
    public String getUserInfo() {
        return "Admin: " + username + " (ID: " + getUserId() + ")";
    }

    @Override
    public String toString() {
        return "Admin ID: " + getUserId() + ", Username: " + username;
    }
}