package factories;

import repositories.*;

 // Lazy-loaded factory for providing singleton instances of repositories.
 // Demo: Factory and Singleton design patterns.
public class RepositoryFactory {

    private static CustomerRepository customerRepository;
    private static ProductRepository productRepository;
    private static OrderRepository orderRepository;
    private static OrderProductRepository orderProductRepository;
    private static CategoryRepository categoryRepository;
    private static AdminRepository adminRepository;

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
}