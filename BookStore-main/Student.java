import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Student extends User {
    private ArrayList<BorrowedBook> borrowedBooks;

    public Student() {
        this.borrowedBooks = new ArrayList<BorrowedBook>();
    }

    public Student(String fullName, String username, String password) {
        super(fullName, username, password);
        this.borrowedBooks = new ArrayList<BorrowedBook>();
    }

    @Override
    public void displayProfile() {
        System.out.println("\n===================================================> Student Profile <===================================================\n");
        
        super.displayProfile();
        
        String whiteSpace = "";
        
        System.out.println("\n===================================================> Borrowed Books <===================================================\n");
        System.out.printf("Book Id%-10s\tName%-40s\tIssue Date%-20s\tReturn Date", whiteSpace, whiteSpace, whiteSpace);
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------");
        
        if(this.borrowedBooks.size() < 1) {
            System.out.println("No books borrowed");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            for(BorrowedBook book: this.borrowedBooks) {
                String returnDateTxt = book.getReturnDate() != null ? dateFormat.format(book.getReturnDate()) : "Not returned"; 
                System.err.printf("%-10s\t\t%-40s\t%-20s\t\t%-20s\n", book.getBookId(), book.getBookName(), dateFormat.format(book.getIssueDate()), returnDateTxt);
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------\n");
    }

    public boolean issueBook(int bookId) {
        Book book = this.findBookById(bookId);
        if(book == null || !book.getIsAvailable()) {
            return false;
        }
        book.setIsAvailable(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        BorrowedBook borrowedBook = null;
        try {
            borrowedBook = new BorrowedBook(book.getBookId(), book.getBookName(), book.getIsAvailable(), dateFormat.parse("01-01-2023"));
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }

        return this.borrowedBooks.add(borrowedBook);
    }

    public int returnBook(int bookId) {
        BorrowedBook borrowedBook = this.findBorrowedBookById(bookId);

        if(borrowedBook == null) {
            return -1;
        }

        if(borrowedBook.getReturnDate() != null) {
            return -2;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse("20-02-2023");
        } catch(Exception exception) {
            System.out.println(exception.getMessage());
            return -1;
        }
        
        borrowedBook.setReturnDate(returnDate);
        borrowedBook.setIsAvailable(true);

        Book book = this.findBookById(bookId);
        book.setIsAvailable(true);

        return IAdmin.calculateFine(borrowedBook.getIssueDate(), borrowedBook.getReturnDate());
    }

    private Book findBookById(int bookId) {
        for(Book book: this.books) {
            if(book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    private BorrowedBook findBorrowedBookById(int bookId) {
        for(BorrowedBook book: this.borrowedBooks) {
            if(book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }
}