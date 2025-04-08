package controllers;

import entities.Product;
import services.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Hämta alla produkter");
        System.out.println("2. Hämta enskild produkt");
        String select = sc.nextLine();

        switch (select) {
            case "1":
                ArrayList<Product> products = productService.getAllProducts();
                for (Product p : products) {
                    System.out.println(p);
                }
                break;
            case "2":
                System.out.println("Mata in kund-ID:");
                int id = sc.nextInt();
                System.out.println(productService.getProduct(id));
                break;
        }
        runMenu();
    }
}
