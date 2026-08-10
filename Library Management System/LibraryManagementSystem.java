import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        boolean running = true;

        while (running) {
            System.out.println("Menu");
            System.out.println("1 - Add Book");
            System.out.println("2 - Display Book");
            System.out.println("3 - Search Book");
            System.out.println("4 - Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addBook(scanner, library);
                    break;
                case "2":
                    library.displayBooks();
                    break;
                case "3":
                    searchBook(scanner, library);
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }

        scanner.close();
    }

    private static void addBook(Scanner scanner, Library library) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        library.addBook(new Book(title, author, year));
    }

    private static void searchBook(Scanner scanner, Library library) {
        System.out.print("Enter a book to search: ");
        String title = scanner.nextLine();
        library.searchBook(title);
    }
}