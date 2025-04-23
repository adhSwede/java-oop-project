package repositories;

import entities.Category;
import utils.SqlUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import static utils.Mappers.categoryMapper;

public class CategoryRepository {

    // #################### [ Get ] ####################
    public ArrayList<Category> getAll() throws SQLException {
        String query = "SELECT * FROM categories";
        return SqlUtils.executeAndMap(query,
                categoryMapper);
    }

    public String getCategoryNameById(int id) throws SQLException {
        String query = "SELECT name FROM categories WHERE category_id = ?";
        ArrayList<String> names = SqlUtils.executeAndMap(query,
                rs -> rs.getString("name"),
                String.valueOf(id));
        return names.isEmpty() ? null : names.getFirst();
    }

    public int getCategoryIdByName(String name) throws SQLException {
        String query = "SELECT category_id FROM categories WHERE name = ?";
        ArrayList<Integer> ids = SqlUtils.executeAndMap(query,
                rs -> rs.getInt("category_id"),
                name);
        return ids.isEmpty() ? -1 : ids.getFirst();
    }

    // #################### [ Insert ] ####################
    public void addCategory(Category category) throws SQLException {
        SqlUtils.executeUpdate("INSERT INTO categories (name) VALUES (?)",
                category.getName());
    }
}