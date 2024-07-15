import java.util.Date;

public interface IAdmin {
    public boolean addBook(Book book);
    public static int calculateFine(Date issueDate, Date returnDate) {
        long issueTime = issueDate.getTime() / (1000 * 60 * 60 * 24);
        long returnTime = returnDate.getTime() / (1000 * 60 * 60 * 24);
        long days = returnTime - issueTime;
        int fineAmount = 0;
        if(days > 15) {
            fineAmount = ( (int) days - 15) * 5;
        }
        return fineAmount;
    }
}