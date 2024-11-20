package com.example.librarymanagementsystem;

public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // Add some books and members
        Book book1 = new Book("1984", "George Orwell", "1234567890");
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", "0987654321");
        Member member1 = new Member("Alice", "M001");
        Member member2 = new Member("Bob", "M002");

        library.addBook(book1);
        library.addBook(book2);
        library.addMember(member1);
        library.addMember(member2);

        // Test borrowing
        System.out.println("Borrowing books:");
        System.out.println(library.borrowBook("1234567890", "M001")); // True
        System.out.println(library.borrowBook("1234567890", "M002")); // False

        // Test returning
        System.out.println("Returning books:");
        System.out.println(library.returnBook("1234567890", "M001")); // True

        // Test searching
        System.out.println("Searching for books:");
        System.out.println(library.searchBooks("Hobbit"));

        // Display statistics
        System.out.println("Library Statistics:");
        System.out.println("Total Books: " + library.totalBooks());
        System.out.println("Total Members: " + library.totalMembers());
        System.out.println("Most Borrowed Book: " + library.mostBorrowedBook());
    }
}

