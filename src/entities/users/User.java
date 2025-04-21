package entities.users;

public abstract class User {
    protected int userId;
    protected String email;
    protected String password;

    // #################### [ Constructor ] ####################
    public User(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    // #################### [ Getters ] ####################
    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // #################### [ Abstract Method ] ####################
    public abstract String getUserInfo();
}
