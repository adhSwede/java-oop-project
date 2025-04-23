package factories;

import services.*;

public class ServiceFactory {

    // Lazy-loaded factory for providing singleton instances of Services.
    // Demo: Factory and Singleton design patterns.
    
    private static AdminService adminService;
    private static CategoryService categoryService;
    private static CustomerService customerService;
    private static ManufacturerService manufacturerService;
    private static OrderService orderService;
    private static ProductService productService;
    private static ReviewService reviewService;
    private static SessionCartService sessionCartService;
    private static StoredCartService storedCartService;

    public static AdminService getAdminService() {
        if (adminService == null) {
            adminService = new AdminService();
        }
        return adminService;
    }

    public static CategoryService getCategoryService() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
    }

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public static ManufacturerService getManufacturerService() {
        if (manufacturerService == null) {
            manufacturerService = new ManufacturerService();
        }
        return manufacturerService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }

    public static ProductService getProductService() {
        if (productService == null) {
            productService = new ProductService();
        }
        return productService;
    }

    public static ReviewService getReviewService() {
        if (reviewService == null) {
            reviewService = new ReviewService();
        }
        return reviewService;
    }

    public static SessionCartService getSessionCartService() {
        if (sessionCartService == null) {
            sessionCartService = new SessionCartService();
        }
        return sessionCartService;
    }

    public static StoredCartService getStoredCartService() {
        if (storedCartService == null) {
            storedCartService = new StoredCartService();
        }
        return storedCartService;
    }
}