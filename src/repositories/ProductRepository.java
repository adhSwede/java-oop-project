package repositories;

import entities.Product;
import utils.Mappers;
import utils.SqlUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.Mappers.productMapper;

public class ProductRepository {

    // #################### [ Get ] ####################

    public ArrayList<Product> getAll() throws SQLException {
        String query = "SELECT * FROM products";
        ArrayList<Product> products = SqlUtils.executeAndMap(query,
                productMapper);
        addCategoriesToProducts(products);
        return products;
    }

    public int getStockByProductId(int productId) throws SQLException {
        String query = "SELECT stock_quantity FROM products WHERE product_id = ?";
        List<Integer> result = SqlUtils.executeAndMap(query, rs -> rs.getInt("stock_quantity"), String.valueOf(productId));
        if (result.isEmpty()) {
            throw new SQLException("Product not found");
        }
        return result.get(0);
    }

    private void addCategoriesToProducts(List<Product> products) throws SQLException {
        for (Product product : products) {
            int productId = product.getProductId();
            String query = """
                        SELECT c.* FROM categories c
                        JOIN products_categories pc ON c.category_id = pc.category_id
                        WHERE pc.product_id = ?
                    """;

            var categories = SqlUtils.executeAndMap(query,
                    Mappers.categoryMapper,
                    String.valueOf(productId));

            product.setCategories(categories);
        }
    }


    public Product getById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE product_id = ?";
        ArrayList<Product> products = SqlUtils.executeAndMap(query,
                productMapper,
                String.valueOf(id));
        return products.isEmpty() ? null : products.getFirst();
    }

    public ArrayList<Product> getByName(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name LIKE ?";
        return SqlUtils.executeAndMap(query,
                productMapper,
                "%" + name + "%");
    }

    public ArrayList<Product> getByCategory(String category) throws SQLException {
        String query = """
                SELECT p.* FROM products p 
                JOIN products_categories pc ON p.product_id = pc.product_id 
                JOIN categories c ON pc.category_id = c.category_id 
                WHERE c.name LIKE ?""";
        return SqlUtils.executeAndMap(query,
                productMapper,
                "%" + category + "%");
    }

    // #################### [ Insert ] ####################

    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) VALUES (?, " +
                "?, ?, ?, ?)";
        SqlUtils.executeUpdate(query,
                product.getManufacturerId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity());
    }

    public int addProductAndReturnId(Product product) throws SQLException {
        String query = "INSERT INTO products (manufacturer_id, name, description, price, stock_quantity) VALUES (?, " +
                "?, ?, ?, ?)";
        return SqlUtils.executeInsertAndReturnId(query,
                product.getManufacturerId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity());
    }

    public void addProductToCategory(int productId,
                                     int categoryId) throws SQLException {
        String query = "INSERT INTO products_categories (product_id, category_id) VALUES (?, ?)";
        SqlUtils.executeUpdate(query,
                productId,
                categoryId);
    }

    // #################### [ Update ] ####################

    public void updatePrice(int id,
                            double price) throws SQLException {
        String query = "UPDATE products SET price = ? WHERE product_id = ?";
        SqlUtils.executeUpdate(query,
                price,
                id);
    }

    public void updateStockQuantity(int id,
                                    int quantity) throws SQLException {
        String query = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        SqlUtils.executeUpdate(query,
                quantity,
                id);
    }
}