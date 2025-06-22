package Dashboard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {
    private Book book;
    private int quantitySold;
    private Date saleDate;

    public Sale(Book book, int quantitySold) {
        this.book = book;
        this.quantitySold = quantitySold;
        this.saleDate = new Date();
    }

    public Book getBook() { return book; }
    public int getQuantitySold() { return quantitySold; }
    public double getTotalAmount() { return book.getPrice() * quantitySold; }
    
    public String getFormattedSaleDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(saleDate);
    }
}