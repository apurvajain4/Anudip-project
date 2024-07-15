public class Admin extends User implements IAdmin {
    public Admin() {}
    public Admin(String fullName, String username, String password) {
        super(fullName, username, password);
    }

    @Override
    public boolean addBook(Book book) {
        return this.books.add(book);
    };
}
