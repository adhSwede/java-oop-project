package services;

import entities.Product;
import repositories.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    ProductRepository productRepository = new ProductRepository();

    public ArrayList<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }

    public Product getProductById(int id) throws SQLException {
        return productRepository.getById(id);
    }

    public ArrayList<Product> getProductByName(String name) throws SQLException {
        return productRepository.getByName(name);
    }

    public ArrayList<Product> getProductByCategory(String category) throws SQLException {
        return productRepository.getByCategory(category);
    }

    public void addProduct(Product product) throws SQLException {
        productRepository.addProduct(product);
    }

    public void updatePrice(int id, double price) throws SQLException {
        productRepository.updatePrice(id, price);
    }

    public void updateStock(int id, int quantity) throws SQLException {
        productRepository.updateStockQuantity(id, quantity);
    }

    public int addProductAndReturnId(Product product) throws SQLException {
        return productRepository.addProductAndReturnId(product);
    }

    public void addProductToCategory(int productId, int categoryId) throws SQLException {
        productRepository.addProductToCategory(productId, categoryId);
    }

}
