import java.util.Date;

public class BorrowedBook extends Book {
    private Date issueDate;
    private Date returnDate;

    public BorrowedBook() {}
    public BorrowedBook(int bookId, String bookName, boolean isAvailable, Date issueDate) {
        super(bookId, bookName, isAvailable);
        this.issueDate = issueDate;
        this.returnDate = null;
    }

    public Date getIssueDate() {
        return this.issueDate;
    }
    
    public Date getReturnDate() {
        return this.returnDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
