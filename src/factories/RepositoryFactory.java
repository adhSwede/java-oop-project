package factories;
import repositories.*;

public class RepositoryFactory {

     // Lazy-loaded factory for providing singleton instances of repositories.
     // Demo: Factory and Singleton design patterns.
    private static CustomerRepository customerRepository;
    private static ProductRepository productRepository;
    private static OrderRepository orderRepository;
    private static OrderProductRepository orderProductRepository;
    private static CategoryRepository categoryRepository;
    private static AdminRepository adminRepository;
    private static StoredCartRepository storedCartRepository;
    private static ReviewRepository reviewRepository;
    private static ManufacturerRepository manufacturerRepository;

    public static CustomerRepository getCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = new CustomerRepository();
        }
        return customerRepository;
    }

    public static ProductRepository getProductRepository() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public static OrderRepository getOrderRepository() {
        if (orderRepository == null) {
            orderRepository = new OrderRepository();
        }
        return orderRepository;
    }

    public static OrderProductRepository getOrderProductRepository() {
        if (orderProductRepository == null) {
            orderProductRepository = new OrderProductRepository();
        }
        return orderProductRepository;
    }

    public static CategoryRepository getCategoryRepository() {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }

    public static AdminRepository getAdminRepository() {
        if (adminRepository == null) {
            adminRepository = new AdminRepository();
        }
        return adminRepository;
    }

    public static StoredCartRepository getStoredCartRepository() {
        if (storedCartRepository == null) {
            storedCartRepository = new StoredCartRepository();
        }
        return storedCartRepository;
    }

    public static ReviewRepository getReviewRepository() {
        if (reviewRepository == null) {
            reviewRepository = new ReviewRepository();
        }
        return reviewRepository;
    }

    public static ManufacturerRepository getManufacturerRepository() {
        if (manufacturerRepository == null) {
            manufacturerRepository = new ManufacturerRepository();
        }
        return manufacturerRepository;
    }
}