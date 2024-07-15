public class Book {
    private int bookId;    
    private String bookName;    
    private boolean isAvailable;
    
    public Book() {}
    public Book(int bookId, String bookName, boolean isAvailable) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.isAvailable = isAvailable;
    }

    public int getBookId() {
        return this.bookId;
    }

    public String getBookName() {
        return this.bookName;
    }
    
    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName= bookName;
    }
    
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
