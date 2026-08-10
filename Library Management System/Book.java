public class Book {

    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String toTableRow() {
        return String.format("%-20s %-15s %-6d", title, author, year);
    }

    public String toDetailString() {
        return "Title: " + title + "\n" +
               "Author: " + author + "\n" +
               "Year: " + year;
    }
}