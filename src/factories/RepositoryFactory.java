package factories;

import repositories.*;

public class RepositoryFactory {

    // Lazy-loaded factory for providing singleton instances of repositories.
    // Demo: Factory and Singleton design patterns.
    private static AdminRepository adminRepository;
    private static CategoryRepository categoryRepository;
    private static CustomerRepository customerRepository;
    private static ManufacturerRepository manufacturerRepository;
    private static OrderProductRepository orderProductRepository;
    private static OrderRepository orderRepository;
    private static ProductRepository productRepository;
    private static ReviewRepository reviewRepository;
    private static StoredCartRepository storedCartRepository;

    public static AdminRepository getAdminRepository() {
        if (adminRepository == null) {
            adminRepository = new AdminRepository();
        }
        return adminRepository;
    }

    public static CategoryRepository getCategoryRepository() {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }

    public static CustomerRepository getCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = new CustomerRepository();
        }
        return customerRepository;
    }

    public static ManufacturerRepository getManufacturerRepository() {
        if (manufacturerRepository == null) {
            manufacturerRepository = new ManufacturerRepository();
        }
        return manufacturerRepository;
    }

    public static OrderProductRepository getOrderProductRepository() {
        if (orderProductRepository == null) {
            orderProductRepository = new OrderProductRepository();
        }
        return orderProductRepository;
    }

    public static OrderRepository getOrderRepository() {
        if (orderRepository == null) {
            orderRepository = new OrderRepository();
        }
        return orderRepository;
    }

    public static ProductRepository getProductRepository() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public static ReviewRepository getReviewRepository() {
        if (reviewRepository == null) {
            reviewRepository = new ReviewRepository();
        }
        return reviewRepository;
    }

    public static StoredCartRepository getStoredCartRepository() {
        if (storedCartRepository == null) {
            storedCartRepository = new StoredCartRepository();
        }
        return storedCartRepository;
    }
}