package services;

import entities.Order;
import entities.OrderProduct;
import entities.Product;
import entities.carts.SessionCart;
import entities.carts.StoredCart;
import exceptions.CartException;
import factories.RepositoryFactory;
import factories.ServiceFactory;
import repositories.OrderProductRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.StoredCartRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoredCartService {
    private final StoredCartRepository storedCartRepository = RepositoryFactory.getStoredCartRepository();
    private final ProductRepository productRepository = RepositoryFactory.getProductRepository();
    private final OrderRepository orderRepository = RepositoryFactory.getOrderRepository();
    private final OrderProductRepository orderProductRepository = RepositoryFactory.getOrderProductRepository();
    private final ProductService productService = ServiceFactory.getProductService();

    // #################### [ Create ] ####################
    public StoredCart createCart(int customerId,
                                 String name) {
        try {
            StoredCart newCart = new StoredCart(-1,
                    customerId,
                    name,
                    false);
            int newId = storedCartRepository.addCart(newCart);
            newCart.setCartId(newId);

            if (storedCartRepository
                    .getCartsByCustomerId(customerId)
                    .size() == 1) {
                storedCartRepository.setActiveCart(customerId,
                        newId);
                newCart.setActive(true);
            }

            return newCart;
        } catch (SQLException e) {
            throw new CartException("Failed to create new cart: " + e.getMessage());
        }
    }

    // #################### [ Read ] ####################
    public StoredCart getActiveCart(int userId) {
        try {
            StoredCart cart = storedCartRepository.getActiveCart(userId); // ✅ Correct method
            if (cart != null) {
                loadCartItems(cart);
            }
            return cart;
        } catch (SQLException e) {
            throw new CartException("Could not retrieve active cart: " + e.getMessage());
        }
    }

    public List<StoredCart> getAllCarts(int customerId) {
        try {
            List<StoredCart> carts = storedCartRepository.getCartsByCustomerId(customerId);
            for (StoredCart cart : carts) {
                loadCartItems(cart);
            }
            return carts;
        } catch (SQLException e) {
            return List.of();
        }
    }

    public void loadCartItems(StoredCart cart) {
        try {
            storedCartRepository.loadItems(cart);
        } catch (SQLException e) {
            throw new CartException("Failed to load items into cart: " + e.getMessage());
        }
    }

    // #################### [ Update ] ####################
    public void setActiveCart(int customerId,
                              int cartId) {
        try {
            StoredCart cart = storedCartRepository.getCartById(cartId);

            if (cart == null) {
                throw new CartException("Cart not found.");
            }

            if (cart.getCustomerId() != customerId) {
                throw new CartException("Not your cart, sorry bud.");
            }

            storedCartRepository.setActiveCart(customerId,
                    cartId);
        } catch (SQLException e) {
            throw new CartException("Could not set active cart: " + e.getMessage());
        }
    }

    public void renameCart(int cartId,
                           String newName) {
        try {
            storedCartRepository.renameCart(cartId,
                    newName);
        } catch (SQLException e) {
            throw new CartException("Failed to rename cart: " + e.getMessage());
        }
    }

    public void addProductToStoredCart(int cartId,
                                       int productId,
                                       int quantity) {
        try {
            Product product = productRepository.getById(productId);

            if (quantity > product.getStockQuantity()) {
                throw new CartException("Only " + product.getStockQuantity() + " units available in stock.");
            }

            storedCartRepository.addItemToCart(cartId,
                    productId,
                    quantity,
                    product.getPrice());
        } catch (SQLException e) {
            throw new CartException("Could not add product to stored cart: " + e.getMessage());
        }
    }

    public void deleteCart(int cartId) throws CartException {
        try {
            storedCartRepository.deleteCart(cartId);
        } catch (SQLException e) {
            throw new CartException("Failed to delete cart: " + e.getMessage());
        }
    }

    // #################### [ Conversion ] ####################
    public void convertCartToOrder(int cartId) {
        try {
            StoredCart cart = storedCartRepository.getCartById(cartId);
            storedCartRepository.loadItems(cart);

            List<OrderProduct> orderProducts = new ArrayList<>();

            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                if (quantity > product.getStockQuantity()) {
                    throw new CartException("Not enough stock for " + product.getName());
                }

                productService.decreaseStock(product.getProductId(), quantity); // 💥 Stock is deducted

                orderProducts.add(new OrderProduct(
                        cart.getCustomerId(),
                        product.getProductId(),
                        quantity,
                        product.getPrice()
                ));
            }

            Order order = new Order(cart.getCustomerId(),
                    LocalDateTime.now());
            int orderId = orderRepository.addOrderAndReturnId(order);

            for (OrderProduct op : orderProducts) {
                op.setOrderId(orderId);
            }

            orderProductRepository.addBatch(orderProducts);
            cart.clearCart();
            storedCartRepository.deleteCart(cartId);

        } catch (SQLException e) {
            throw new CartException("Order conversion failed: " + e.getMessage());
        }
    }

    public StoredCart convertSessionToStoredCart(int customerId,
                                                 String cartName,
                                                 SessionCart sessionCart) {
        StoredCart newCart = createCart(customerId,
                cartName);

        for (Map.Entry<Product, Integer> entry : sessionCart
                .getItems()
                .entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            try {
                addProductToStoredCart(newCart.getCartId(),
                        product.getProductId(),
                        quantity);
            } catch (CartException e) {
                System.err.println("Could not add product " + product.getProductId() + ": " + e.getMessage());
            }
        }

        return newCart;
    }
}