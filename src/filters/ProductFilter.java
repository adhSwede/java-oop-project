package filters;

import entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductFilter {

    public static List<Product> filterByCategory(List<Product> products, String categoryName) {

        return products.stream()
                .filter(p -> p.getCategories().stream()
                        .anyMatch(cat -> cat.getName().toLowerCase().contains(categoryName.toLowerCase())))
                .collect(Collectors.toList());
    }

    public static List<Product> filterByPriceRange(List<Product> products, double min, double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public static List<Product> filterByCategoryAndPriceRange(
            List<Product> productsFromCategory,
            double min,
            double max) {
        return filterByPriceRange(productsFromCategory, min, max);
    }
}