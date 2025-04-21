package repositories;

import entities.Product;
import entities.carts.StoredCart;
import factories.RepositoryFactory;
import utils.SqlUtils;
import utils.Mappers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StoredCartRepository {

    // #################### [ Basic Cart CRUD ] ####################

    public int addCart(StoredCart cart) throws SQLException {
        String query = "INSERT INTO carts (customer_id, name, is_active) VALUES (?, ?, ?)";
        return SqlUtils.executeInsertAndReturnId(query, cart.getCustomerId(), cart.getName(), cart.isActive());
    }

    public StoredCart getCartById(int cartId) throws SQLException {
        String query = "SELECT * FROM carts WHERE cart_id = ?";
        var result = SqlUtils.executeAndMap(query, Mappers.storedCartMapper, String.valueOf(cartId));
        return result.isEmpty() ? null : result.get(0);
    }

    public List<StoredCart> getCartsByCustomerId(int customerId) throws SQLException {
        String query = "SELECT * FROM carts WHERE customer_id = ?";
        return SqlUtils.executeAndMap(query, Mappers.storedCartMapper, String.valueOf(customerId));
    }

    public void renameCart(int cartId, String newName) throws SQLException {
        String query = "UPDATE carts SET name = ? WHERE cart_id = ?";
        SqlUtils.executeUpdate(query, newName, cartId);
    }

    public void deleteCart(int cartId) throws SQLException {
        String query = "DELETE FROM carts WHERE cart_id = ?";
        SqlUtils.executeUpdate(query, cartId);
    }

    // #################### [ Cart State Management ] ####################

    public void setActiveCart(int customerId, int cartId) throws SQLException {
        String deactivateQuery = "UPDATE carts SET is_active = 0 WHERE customer_id = ?";
        String activateQuery = "UPDATE carts SET is_active = 1 WHERE cart_id = ?";

        SqlUtils.executeUpdate(deactivateQuery, customerId);
        SqlUtils.executeUpdate(activateQuery, cartId);
    }

    public StoredCart getActiveCart(int customerId) throws SQLException {
        String query = "SELECT * FROM carts WHERE customer_id = ? AND is_active = 1 LIMIT 1";
        var result = SqlUtils.executeAndMap(query, Mappers.storedCartMapper, String.valueOf(customerId));
        return result.isEmpty() ? null : result.get(0);
    }

    public Integer getActiveCartIdByCustomerId(int customerId) throws SQLException {
        String query = "SELECT cart_id FROM carts WHERE customer_id = ? AND is_active = 1 LIMIT 1";
        var result = SqlUtils.executeAndMap(query, rs -> rs.getInt("cart_id"), String.valueOf(customerId));
        return result.isEmpty() ? null : result.get(0);
    }

    // #################### [ Cart Item Handling ] ####################

    public void addItemToCart(int cartId, int productId, int quantity, double unitPrice) throws SQLException {
        String query = "INSERT INTO order_products (cart_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        SqlUtils.executeUpdate(query, cartId, productId, quantity, unitPrice);
    }

    public void loadItems(StoredCart cart) throws SQLException {
        String query = "SELECT product_id, quantity FROM order_products WHERE cart_id = ?";
        var rows = SqlUtils.executeAndMap(query, rs -> {
            int productId = rs.getInt("product_id");
            int quantity = rs.getInt("quantity");
            return Map.entry(productId, quantity);
        }, String.valueOf(cart.getCartId()));

        for (var entry : rows) {
            Product product = RepositoryFactory.getProductRepository().getById(entry.getKey());
            cart.addProduct(product, entry.getValue());
        }
    }
}
