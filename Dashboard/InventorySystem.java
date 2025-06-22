package Dashboard;

import java.util.ArrayList;

public class InventorySystem {
    private ArrayList<Book> books;
    private ArrayList<Sale> sales;

    public InventorySystem() {
        books = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public boolean addBook(String isbn, String title, String author, String genre, double price, int stockLevel) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return false;
            }
        }
        books.add(new Book(isbn, title, author, genre, price, stockLevel));
        return true;
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public boolean updateStock(String isbn, int newStockLevel) {
        Book book = findBookByIsbn(isbn);
        if (book != null && newStockLevel >= 0) {
            book.setStockLevel(newStockLevel);
            return true;
        }
        return false;
    }

    public double processSale(String isbn, int quantity) {
        Book book = findBookByIsbn(isbn);
        if (book == null || book.getStockLevel() < quantity || quantity <= 0) {
            return -1;
        }
        book.setStockLevel(book.getStockLevel() - quantity);
        Sale sale = new Sale(book, quantity);
        sales.add(sale);
        return sale.getTotalAmount();
    }

    public ArrayList<Sale> getSalesForDate(String date) {
        ArrayList<Sale> result = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getFormattedSaleDate().equals(date)) {
                result.add(sale);
            }
        }
        return result;
    }

    public double getTotalSalesForDate(String date) {
        double total = 0;
        for (Sale sale : sales) {
            if (sale.getFormattedSaleDate().equals(date)) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }
}