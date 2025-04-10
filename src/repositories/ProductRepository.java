package repositories;
import entities.Product;
import utils.SqlUtils;

import java.sql.*;
import java.util.ArrayList;

import static utils.Mappers.productMapper;

public class ProductRepository {



    // Get all products
    public ArrayList<Product> getAll() throws SQLException {
        String query = "SELECT * FROM products";

        return SqlUtils.executeAndMap(query, productMapper);
    }

    // Get product by ID
    public Product getById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE product_id = ?";

        ArrayList<Product> products = SqlUtils.executeAndMap(
                query,
                productMapper,
                String.valueOf(id));

        return products.isEmpty() ? null : products.getFirst();
    }

    // Get product by name
    public ArrayList<Product> getByName(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name LIKE ?";

        return SqlUtils.executeAndMap(query, productMapper, "%" + name + "%");
    }

    // Get product by category
    public ArrayList<Product> getByCategory(String category) throws SQLException {
        String query = "SELECT p.* FROM products p " +
                       "JOIN products_categories pc ON p.product_id = pc.product_id " +
                       "JOIN categories c ON pc.category_id = c.category_id " +
                       "WHERE c.name LIKE ?";

        return SqlUtils.executeAndMap(query, productMapper, "%" + category + "%");
    }

    // Add new product
    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?)";
        SqlUtils.executeUpdate(query,
                product.getManufacturerId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity());
    }

    // Update product price
    public void updatePrice(int id, double price) throws SQLException {
        String query = "UPDATE products SET price = ? WHERE product_id = ?";
        SqlUtils.executeUpdate(query, price, id);
    }

    // Update stock quantity
    public void updateStockQuantity(int id, int quantity) throws SQLException {
        String query = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        SqlUtils.executeUpdate(query, quantity, id);
    }

    public int addProductAndReturnId(Product product) throws SQLException {
        String query = "INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?)";
        return SqlUtils.executeInsertAndReturnId(
                query,
                product.getManufacturerId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    public void addProductToCategory(int productId, int categoryId) throws SQLException {
        String query = "INSERT INTO products_categories (product_id, category_id) VALUES (?, ?)";
        SqlUtils.executeUpdate(query, productId, categoryId);
    }
}