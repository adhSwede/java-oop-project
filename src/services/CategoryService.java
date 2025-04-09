package services;

import entities.Category;
import repositories.CategoryRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryService {
    CategoryRepository categoryRepository = new CategoryRepository();

    public ArrayList<Category> getAllCategories() throws SQLException {
        return categoryRepository.getAll();
    }

    public String getCategoryNameById(int id) throws SQLException {
        return categoryRepository.getCategoryNameById(id);
    }

    public int getOrCreateCategoryId(String name) throws SQLException {
        int id = categoryRepository.getCategoryIdByName(name);
        if (id == -1) {
            categoryRepository.addCategory(new Category(name));
            id = categoryRepository.getCategoryIdByName(name);
        }
        return id;
    }
}
