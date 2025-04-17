package factories;

import services.*;

public class ServiceFactory {
    private static final AdminService adminService = new AdminService();
    private static final CategoryService categoryService = new CategoryService();
    private static final CustomerService customerService = new CustomerService();
    private static final OrderService orderService = new OrderService();
    private static final ProductService productService = new ProductService();
    private static final SessionCartService sessionCartService = new SessionCartService();
    private static final StoredCartService storedCartService = new StoredCartService();

    public static AdminService getAdminService() {
        return adminService;
    }

    public static CategoryService getCategoryService() {
        return categoryService;
    }

    public static CustomerService getCustomerService() {
        return customerService;
    }

    public static OrderService getOrderService() {
        return orderService;
    }

    public static ProductService getProductService() {
        return productService;
    }

    public static SessionCartService getSessionCartService() {
        return sessionCartService;
    }

    public static StoredCartService getStoredCartService() {
        return storedCartService;
    }
}
