package com.example.librarymanagementsystem;

import java.io.IOException;
import java.time.LocalDate;

public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // Populate library with 10 fake books and 10 fake members
        populateLibrary(library);

        // Save the populated data to the file
        try {
            library.saveData();
            System.out.println("Library data saved to library_data.dat.");
        } catch (IOException e) {
            System.out.println("Failed to save library data: " + e.getMessage());
        }

        // Load the data back and test features
        try {
            library.loadData();
            System.out.println("Library data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load library data: " + e.getMessage());
        }

        // Extended tests
        testLibraryFeatures(library);
    }

    // Method to populate library with fake data
    private static void populateLibrary(Library library) {
        // Add 10 fake books
        library.addBook(new Book("The Great Adventure", "John Smith", "1234567890"));
        library.addBook(new Book("Mystery of the Missing Code", "Jane Doe", "0987654321"));
        library.addBook(new Book("Java for Pros", "Alice Johnson", "1122334455"));
        library.addBook(new Book("The Art of Debugging", "Robert Brown", "5566778899"));
        library.addBook(new Book("Understanding Algorithms", "Emily Davis", "6677889900"));
        library.addBook(new Book("Design Patterns Explained", "William Moore", "2233445566"));
        library.addBook(new Book("Mastering OOP", "Emma Wilson", "4455667788"));
        library.addBook(new Book("Database Essentials", "Sophia Martinez", "8899001122"));
        library.addBook(new Book("Software Testing Handbook", "Michael Garcia", "9900112233"));
        library.addBook(new Book("The Cloud Revolution", "Olivia Anderson", "3344556677"));

        // Add 10 fake members
        library.addMember(new Member("Ethan Taylor", "M001"));
        library.addMember(new Member("Mia Thomas", "M002"));
        library.addMember(new Member("James White", "M003"));
        library.addMember(new Member("Charlotte Harris", "M004"));
        library.addMember(new Member("Benjamin Martin", "M005"));
        library.addMember(new Member("Amelia Clark", "M006"));
        library.addMember(new Member("Lucas Lewis", "M007"));
        library.addMember(new Member("Isabella Young", "M008"));
        library.addMember(new Member("Henry Hall", "M009"));
        library.addMember(new Member("Sophia Walker", "M010"));
    }

    // Method to test library features
    private static void testLibraryFeatures(Library library) {
        // Test adding a book
        System.out.println("\n--- Testing Add Book ---");
        Book newBook = new Book("New Book Title", "Author Name", "1112223334");
        library.addBook(newBook);
        System.out.println("Book added: " + newBook);

        // Test removing a book
        System.out.println("\n--- Testing Remove Book ---");
        library.removeBook("1234567890"); // Remove "The Great Adventure"
        System.out.println("Book with ISBN 1234567890 removed.");

        // Test borrowing a book
        System.out.println("\n--- Testing Borrow Book ---");
        boolean borrowSuccess = library.borrowBook("0987654321", "M001");
        System.out.println(borrowSuccess ? "Book borrowed successfully." : "Borrowing failed.");

        // Test returning a book
        System.out.println("\n--- Testing Return Book ---");
        boolean returnSuccess = library.returnBook("0987654321", "M001");
        System.out.println(returnSuccess ? "Book returned successfully." : "Return failed.");

        // Test viewing active loans
        System.out.println("\n--- Testing Active Loans ---");
        library.borrowBook("1122334455", "M002"); // Borrow "Java for Pros"
        library.borrowBook("5566778899", "M003"); // Borrow "The Art of Debugging"
        for (Book book : library.getBookList()) {
            if (!book.isAvailable()) {
                System.out.println("Active Loan: " + book.getTitle() + " (Borrower ID: " + book.getBorrowerID() + ")");
            }
        }

        // Test overdue tracking
        System.out.println("\n--- Testing Overdue Books ---");
        for (Book book : library.getBookList()) {
            if (!book.isAvailable()) {
                // Simulate an overdue book by setting a past borrowed date
                book.setBorrowedDate(LocalDate.now().minusWeeks(2));
                System.out.println(book.getTitle() + " is overdue: " + book.isOverdue());
            }
        }

        // Test member borrow history
        System.out.println("\n--- Testing Member Borrow History ---");
        for (Member member : library.getMemberList()) {
            System.out.println("Member: " + member.getName() + ", Borrow History: " + member.getBorrowHistory());
        }
    }
}
