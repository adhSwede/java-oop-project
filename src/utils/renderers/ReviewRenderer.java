package renderers;

import entities.Review;
import utils.ConsoleHelper;

import static constants.Colors.BOLD;
import static constants.Colors.RESET;

public class ReviewRenderer {

    public static void printReview(Review r) {
        System.out.println(BOLD + "📝 Review by Customer #" + r.getCustomerId() + RESET);
        System.out.println("Rating: " + ConsoleHelper.renderStars(r.getRating()));

        String comment = r.getComment();
        comment = (comment == null || comment.isEmpty()) ? "(No comment)" : "\"" + comment + "\"";
        System.out.println(comment);

        ConsoleHelper.printDivider();
    }
}
