import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully!");
    }

    public void displayBooks() {
        System.out.println("Book List:");
        System.out.printf("%-20s %-15s %-6s%n", "Title", "Author", "Year");
        for (Book book : books) {
            System.out.println(book.toTableRow());
        }
    }

    public void searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book found!");
                System.out.println(book.toDetailString());
                return;
            }
        }
        System.out.println("Book not found!");
    }
}