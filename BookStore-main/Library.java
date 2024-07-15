import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library {
    private ArrayList<User> users;
    private ArrayList<Book> books;
    private Scanner scan;
    private Console con;

    private Library() {
        this.users = new ArrayList<User>();
        this.books = new ArrayList<Book>();
        this.scan = new Scanner(System.in);
        this.con = System.console();
    }

    public static Library makeInstance() {
        return new Library();
    }

    public void begin() {
        this.displayHomeMenu();
    }

    private void displayHomeHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("+-------------------------------------------+");
        System.out.println("|                                           |");
        System.out.println("| ======> Library Management System <====== |");
        System.out.println("|                                           |");
        System.out.println("+-------------------------------------------+");
        System.out.println();
    }

    private void displayHomeMenuTxt() {
        this.displayHomeHeader();
        System.out.println("1. Sign In");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Enter your choice: ");
    }

    private void displayHomeMenu() {
        int choice = -1;

        while(choice != 3) {
            this.displayHomeMenuTxt();
            choice = this.scan.nextInt();

            switch(choice) {
                case 1:
                    this.displaySignInMenu();
                    break;
                case 2:
                    this.handleRegistration();
                    break;
                case 3:
                    System.out.println("\nGoodbye...");
                    break;
                default:
                    System.out.println("\nINVALID INPUT\n");
                    System.out.println("Press enter to continue");
                    this.con.readLine();
            }
        }
    }

    private void displaySignInMenu() {
        int choice = -1;
        while (choice != 1 && choice != 2) {
            System.out.println("\nChoose an option:- ");
            System.out.println("1. Enter sign in details");
            System.out.println("2. Forgot password");
            System.out.println("Enter your choice: ");
            choice = this.scan.nextInt();
            switch (choice) {
                case 1:
                    User user = this.handleSignIn();
                    if (user != null) {
                        this.handleSignedInUser(user);
                    }
                    break;
                case 2:
                    this.handleForgotPassword();
                    break;
                default:
                    System.out.println("\nINVALID INPUT\n");
                    System.out.println("Press enter to continue");
                    this.con.readLine();
            }
        }
    } 

    private User handleSignIn() {
        String username = this.getUsernameInput();
        String password = this.getPasswordInput("Enter password: ");
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    System.out.println("\nInvalid password\n");
                    System.out.println("Press enter to continue");
                    this.con.readLine();
                    return null;
                }
            }
        }
        System.out.println("\nUser is not registered\n");
        System.out.println("Press enter to continue");
        this.con.readLine();
        return null;
    }

    private void handleSignedInUser(User user) {
        System.out.println("\nSuccessfully signed in\n");
        System.out.println("Press enter to continue");
        this.con.readLine();

        if(this.isAdmin(user)) {
            this.displayAdminView((Admin) user);
        } else {
            this.displayStudentView((Student) user);
        }
    }

    private void displayAdminView(Admin adminUser) {
        int choice = -1;
        while(choice != 2) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\n===================================================> Admin Panel <===================================================\n");
            
            this.displayBookCatalogue();
            
            System.out.println("Choose an option");
            System.out.println("1. Add book");
            System.out.println("2. Logout");
            System.out.println("Enter your choice: ");
            choice = this.scan.nextInt();

            switch(choice) {
                case 1:
                    if(adminUser.addBook(getBookInput())) {
                        System.out.println("\nBook added successfully to the catalogue\n");
                    } else {
                        System.out.println("\nFailed to add book to the catalogue\n");
                    }
                    break;

                case 2:
                    System.out.println("\nYou are logged out\n");
                    break;

                default:
                    System.out.println("\nINVALID INPUT\n");
            }
            
            System.out.println("Press enter to continue");
            this.con.readLine();
        }
    }

    private void displayStudentView(Student studentUser) {
        int choice = -1;
        while(choice != 4) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            System.out.println("\n==================================================> Student Panel <==================================================\n");
            
            this.displayBookCatalogue();
            
            System.out.println("Choose an option");
            System.out.println("1. Issue Book");
            System.out.println("2. Return Book");
            System.out.println("3. Display Profile");
            System.out.println("4. Logout");
            System.out.println("Enter your choice: ");
            choice = this.scan.nextInt();

            switch(choice) {
                case 1:
                    if(!studentUser.issueBook(this.getBookIdInput())) {
                        System.out.println("\nBook not available\n");
                    } else {
                        System.out.println("\nBook issued successfully\n");
                    }
                    break;
                
                case 2:
                    int status = studentUser.returnBook(this.getBookIdInput());
                    
                    if(status == -1) {
                        System.out.println("\nBook not available\n");
                    } else if(status == -2) {
                        System.out.println("\nCannot re-return the book\n");
                    } else {
                        System.out.println("\nBook returned successfully\n");
                    }
                    
                    if(status > 0) {
                        System.out.println("Total Fine of " + status + " rupees due to late return of book by " + status / 5 + " days\n");
                    }
                    break;
                
                case 3:
                    studentUser.displayProfile();
                    break;

                case 4:
                    System.out.println("\nYou are logged out\n");
                    break;

                default:
                    System.out.println("\nINVALID INPUT\n");
            }

            System.out.println("Press enter to continue");
            this.con.readLine();
        }
    }

    private int getBookIdInput() {
        int bookId;
        System.out.println("Enter book id: ");
        bookId = this.scan.nextInt();
        return bookId;
    }

    private Book getBookInput() {
        int bookId;
        String bookName;
        boolean isAvailable = false;

        System.out.println("Enter book id:");
        bookId = this.scan.nextInt();
        
        System.out.println("Enter book name:");
        this.scan.nextLine();
        bookName = this.scan.nextLine();
        bookName = bookName.trim();

        int choice = -1;
        while(choice != 1 && choice != 2) {
            System.out.println("\nIs book available: ");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("Choose an option:");
            choice = this.scan.nextInt();
            
            if(choice != 1 && choice != 2) {
                System.out.println("\nINVALID INPUT\n");
            } else {
                isAvailable = choice == 1;
            }
        }
        return new Book(bookId, bookName, isAvailable);
    }

    private void handleForgotPassword() {
        String fullName = this.getFullNameInput();
        if (this.resetPassword(fullName)) {
            System.out.println("\nSuccessfully changed the password\n");
        } else {
            System.out.println("\nFull name doesn't exists\n");
        }
        System.out.println("Press enter to continue");
        this.con.readLine();
    }

    private boolean resetPassword(String fullName) {
        for (User user : this.users) {
            if (user.getFullName().equals(fullName)) {
                user.setPassword(this.getPasswordInput("Enter new password: "));
                return true;
            }
        }
        return false;
    }

    private void handleRegistration() {
        User newUser = this.getRegistrationInput();
        String userRoleTxt = this.isAdmin(newUser) ? "an Admin" : "a Student";
        if(this.users.add(newUser)) {
            System.out.println("\nRegistered successfully as " + userRoleTxt + "\n");
        } else {
            System.out.println("\nFailed to register\n");
        }
        System.out.println("Press enter to continue");
        this.con.readLine();
    }

    private User getRegistrationInput() {
        String fullName = this.getFullNameInput();
        String username = this.getUsernameInput();
        String password = this.getPasswordInput("Enter password: ");
        boolean isAdmin = false;
        int choice = -1;
        while(choice != 1 && choice != 2) {
            System.out.println("\nRegister as:");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.println("Choose an option: ");
            choice = this.scan.nextInt();

            if(choice == 1 || choice == 2) {
                isAdmin = choice == 1;
            } else {
                System.out.println("\nINVALID INPUT\n");
            }
        }

        User newUser = isAdmin ? new Admin() : new Student();
        newUser.setFullName(fullName);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.giveLibraryAccess(this.books);
        
        return newUser;
    } 

    private boolean isAdmin(User user) {
        return user instanceof Admin;
    }

    private void displayBookCatalogue() {
        String whiteSpace = "";
        
        System.out.println("\n=================================================> Book Catalogue <=================================================\n");
        System.out.printf("Book Id%-10s\tName%-40s\tAvailable", whiteSpace, whiteSpace);
        System.out.println("\n--------------------------------------------------------------------------------------");
        
        if(this.books.size() < 1) {
            System.out.println("Books not available");
        }
        
        for(Book book: this.books) {
            String isAvailableTxt = book.getIsAvailable() ? "Available" : "Not Available";
            System.out.printf("%-10s\t\t%-40s\t%-10s\n", book.getBookId(), book.getBookName(), isAvailableTxt);
        }
        
        System.out.println("--------------------------------------------------------------------------------------\n\n");
    }

    private String getUsernameInput() {
        System.out.println("Enter username: ");
        String username = this.scan.next();
        return username;
    }

    private String getFullNameInput() {
        System.out.println("Enter full name: ");
        this.scan.nextLine();
        String fullName = this.scan.nextLine();
        return fullName.trim();
    }

    private String getPasswordInput(String message) {
        if (this.con == null) {
            System.out.println("\nConsole is not available\n");
            return null;
        }
        String password = "";
        boolean isPasswordInputValid = true;
        while(isPasswordInputValid) {
            System.out.print(message);
            char[] pass;
            pass = this.con.readPassword();
            for (int i = 0; i < pass.length; i++) {
                System.out.print("*");
            }
            System.out.println();
            password = new String(pass);
            if(this.validatePassword(password)) {
                isPasswordInputValid = false;
            } else {
                System.out.println("Password should be 6 to 18 characters long and it should consist a lowercase letter, a digit, a special character and no white space");
            }
        }
        return password;
    }

    private boolean validatePassword(String password) {
        String passwordRegExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{6,18}$";
        Pattern pattern = Pattern.compile(passwordRegExp);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
