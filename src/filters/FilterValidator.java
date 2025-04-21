package filters;

import exceptions.FilterException;
import services.CategoryService;

public class FilterValidator {

    private FilterValidator() {
    } // Prevent instantiation

    public static void validatePriceRange(double min, double max) {
        if (min < 0 || max < 0 || min > max) {
            throw new FilterException("Invalid price range: Min must be ≤ Max and both non-negative.");
        }
    }

    public static void validateCategoryName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new FilterException("Category name cannot be empty.");
        }
    }

    public static boolean validateCategoryExists(String categoryName, CategoryService categoryService) {
        try {
            return categoryService.getAllCategories().stream()
                    .anyMatch(cat -> cat.getName().toLowerCase().contains(categoryName.toLowerCase()));
        } catch (Exception e) {
            System.err.println("Failed to validate category: " + e.getMessage());
            return false;
        }
    }
}

