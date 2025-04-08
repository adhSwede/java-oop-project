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

    public Product getProduct(int id) throws SQLException {
        return productRepository.getById(id);
    }
}
