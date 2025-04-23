package services;

import entities.Product;
import factories.RepositoryFactory;
import repositories.ProductRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    private final ProductRepository productRepository = RepositoryFactory.getProductRepository();

    // #################### [ Get ] ####################
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

    // #################### [ Insert ] ####################
    public void addProduct(Product product) throws SQLException {
        productRepository.addProduct(product);
    }

    public int addProductAndReturnId(Product product) throws SQLException {
        return productRepository.addProductAndReturnId(product);
    }

    public void addProductToCategory(int productId,
                                     int categoryId) throws SQLException {
        productRepository.addProductToCategory(productId,
                categoryId);
    }

    // #################### [ Update ] ####################
    public void updatePrice(int id,
                            double price) throws SQLException {
        productRepository.updatePrice(id,
                price);
    }

    public void updateStock(int id,
                            int quantity) throws SQLException {
        productRepository.updateStockQuantity(id,
                quantity);
    }

    public void increaseStock(int productId, int quantity) throws SQLException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive to increase stock.");
        }

        int preUpdateStock = productRepository.getStockByProductId(productId);
        updateStock(productId, preUpdateStock + quantity);
    }

    public void decreaseStock(int productId, int quantity) throws SQLException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive to decrease stock.");
        }

        int preUpdateStock = productRepository.getStockByProductId(productId);
        if (quantity > preUpdateStock) {
            throw new IllegalArgumentException("Cannot decrease stock below zero.");
        }

        updateStock(productId, preUpdateStock - quantity);
    }
}