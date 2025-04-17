package entities.carts;

import entities.Product;
import services.ProductService;
import utils.ExceptionHandler;

import java.sql.SQLException;

public class SessionCart extends Cart {
    private final ProductService productService = new ProductService();

    public void addProductToCart(int productId, int quantity) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                addProduct(product, quantity);
            }
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "adding product to session cart");
        }
    }

    public void removeProductFromCart(int productId) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                removeProduct(product);
            }
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "removing product from session cart");
        }
    }

    public void updateProductQuantity(int productId, int quantity) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                updateQuantity(product, quantity);
            }
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "updating quantity in session cart");
        }
    }
}
