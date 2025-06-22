package Dashboard;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private double price;
    private int stockLevel;

    public Book(String isbn, String title, String author, String genre, double price, int stockLevel) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    // Getters and setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public double getPrice() { return price; }
    public int getStockLevel() { return stockLevel; }
    public void setStockLevel(int stockLevel) { this.stockLevel = stockLevel; }
}