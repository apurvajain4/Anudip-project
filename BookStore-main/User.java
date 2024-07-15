import java.util.ArrayList;

public abstract class User {
    private String fullName;    
    private String username;    
    private String password;

    protected ArrayList<Book> books;

    public User() {}
    public User(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Book> getBooks() {
        return this.books;
    }
   
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void giveLibraryAccess(ArrayList<Book> books) {
        this.books = books;
    }

    public void displayProfile() {
        System.out.println("Full name: " + this.fullName);
        System.out.println("username: " + this.username);
    }
}
