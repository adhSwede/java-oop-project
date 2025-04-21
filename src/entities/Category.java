package entities;

public class Category {

    private int id;
    private String name;

    // #################### [ Constructors ] ####################
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    // #################### [ Getters ] ####################
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // #################### [ ToString ] ####################
    @Override
    public String toString() {
        return "Category ID: " + id + ", Name: " + name;
    }
}