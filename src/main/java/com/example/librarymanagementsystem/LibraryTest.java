package com.example.librarymanagementsystem;

import java.io.File;
import java.util.List;

public class LibraryTest {

    public static void main(String[] args) {
        Library library = new Library();

        // Load data into the library or create and fill the data file if it doesn't exist
        File dataFile = new File("library_data.dat");
        if (!dataFile.exists()) {
            System.out.println("No existing data found. Creating and populating library_data.dat...");
            populateDefaultLibraryData(library);
            try {
                library.saveData();
                System.out.println("library_data.dat created and populated successfully.");
            } catch (Exception e) {
                System.out.println("Failed to save library data: " + e.getMessage());
            }
        } else {
            try {
                library.loadData();
                System.out.println("Library data loaded successfully.");
            } catch (Exception e) {
                System.out.println("Failed to load library data: " + e.getMessage());
            }
        }

        // Run tests
        System.out.println("\nRunning Tests...");
        runTests(library);
    }

    private static void runTests(Library library) {
        testAddBook(library);
        testAddMember(library);
        testBorrowBook(library);
        testReturnBook(library);
        testDuplicateISBN(library);
        testDuplicateMemberID(library);
        testDeleteBook(library);
        testDeleteMember(library);
        testActiveLoans(library);
        System.out.println("\nAll tests completed.");
    }

    private static void populateDefaultLibraryData(Library library) {
        // Add 10 fake books
        library.addBook(new Book("The Great Adventure", "Alice Smith", "111111111"));
        library.addBook(new Book("Ocean's Mystery", "Bob Johnson", "222222222"));
        library.addBook(new Book("Stars Above", "Cathy Brown", "333333333"));
        library.addBook(new Book("Future Tech", "David Wilson", "444444444"));
        library.addBook(new Book("Cooking Secrets", "Emma Davis", "555555555"));
        library.addBook(new Book("Gardening Tips", "Frank White", "666666666"));
        library.addBook(new Book("History of Time", "Grace Martin", "777777777"));
        library.addBook(new Book("Art of Painting", "Hannah Thompson", "888888888"));
        library.addBook(new Book("Code Mastery", "Isaac Taylor", "999999999"));
        library.addBook(new Book("Mountains High", "Jack Moore", "101010101"));

        // Add 10 fake members
        library.addMember(new Member("John Doe", "MEM001"));
        library.addMember(new Member("Jane Smith", "MEM002"));
        library.addMember(new Member("Bob Lee", "MEM003"));
        library.addMember(new Member("Alice Kim", "MEM004"));
        library.addMember(new Member("Tom Ford", "MEM005"));
        library.addMember(new Member("Emma Watson", "MEM006"));
        library.addMember(new Member("Charlie Brown", "MEM007"));
        library.addMember(new Member("Sophia Turner", "MEM008"));
        library.addMember(new Member("Liam Wilson", "MEM009"));
        library.addMember(new Member("Olivia Johnson", "MEM010"));
    }

    private static void testAddBook(Library library) {
        System.out.println("Testing Add Book...");
        Book book = new Book("Test Title", "Test Author", "123456789");
        library.addBook(book);
        assert library.getBookList().contains(book) : "Add Book Failed!";
        System.out.println("Add Book Test Passed.");
    }

    private static void testAddMember(Library library) {
        System.out.println("Testing Add Member...");
        Member member = new Member("Test Member", "MEM101");
        library.addMember(member);
        assert library.getMemberList().contains(member) : "Add Member Failed!";
        System.out.println("Add Member Test Passed.");
    }

    private static void testBorrowBook(Library library) {
        System.out.println("Testing Borrow Book...");
        String isbn = "111111111";
        String memberId = "MEM001";
        library.borrowBook(isbn, memberId);
        Book borrowedBook = library.getBookList().stream()
                .filter(book -> book.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
        assert borrowedBook != null && !borrowedBook.isAvailable() : "Borrow Book Failed!";
        System.out.println("Borrow Book Test Passed.");
    }

    private static void testReturnBook(Library library) {
        System.out.println("Testing Return Book...");
        String isbn = "111111111";
        String memberId = "MEM001";
        library.returnBook(isbn, memberId);
        Book returnedBook = library.getBookList().stream()
                .filter(book -> book.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
        assert returnedBook != null && returnedBook.isAvailable() : "Return Book Failed!";
        System.out.println("Return Book Test Passed.");
    }

    private static void testDuplicateISBN(Library library) {
        System.out.println("Testing Duplicate ISBN...");
        Book duplicateBook = new Book("Duplicate Title", "Duplicate Author", "111111111");
        library.addBook(duplicateBook);
        long count = library.getBookList().stream()
                .filter(book -> book.getISBN().equals("111111111"))
                .count();
        assert count == 1 : "Duplicate ISBN Test Failed!";
        System.out.println("Duplicate ISBN Test Passed.");
    }

    private static void testDuplicateMemberID(Library library) {
        System.out.println("Testing Duplicate Member ID...");
        Member duplicateMember = new Member("Duplicate Member", "MEM001");
        library.addMember(duplicateMember);
        long count = library.getMemberList().stream()
                .filter(member -> member.getMemberID().equals("MEM001"))
                .count();
        assert count == 1 : "Duplicate Member ID Test Failed!";
        System.out.println("Duplicate Member ID Test Passed.");
    }

    private static void testDeleteBook(Library library) {
        System.out.println("Testing Delete Book...");
        String isbnToDelete = "111111111";
        Book bookToDelete = library.getBookList().stream()
                .filter(book -> book.getISBN().equals(isbnToDelete))
                .findFirst()
                .orElse(null);
        if (bookToDelete != null) {
            library.getBookList().remove(bookToDelete);
        }
        assert library.getBookList().stream()
                .noneMatch(book -> book.getISBN().equals(isbnToDelete)) : "Delete Book Failed!";
        System.out.println("Delete Book Test Passed.");
    }

    private static void testDeleteMember(Library library) {
        System.out.println("Testing Delete Member...");
        String memberIdToDelete = "MEM001";
        Member memberToDelete = library.getMemberList().stream()
                .filter(member -> member.getMemberID().equals(memberIdToDelete))
                .findFirst()
                .orElse(null);
        if (memberToDelete != null) {
            List<String> borrowedBooks = memberToDelete.getBorrowedBooks();
            borrowedBooks.forEach(isbn -> library.returnBook(isbn, memberIdToDelete));
            library.getMemberList().remove(memberToDelete);
        }
        assert library.getMemberList().stream()
                .noneMatch(member -> member.getMemberID().equals(memberIdToDelete)) : "Delete Member Failed!";
        System.out.println("Delete Member Test Passed.");
    }

    private static void testActiveLoans(Library library) {
        System.out.println("Testing Active Loans...");
        Book book = new Book("Loan Test Title", "Loan Test Author", "987654321");
        Member member = new Member("Loan Test Member", "MEM102");
        library.addBook(book);
        library.addMember(member);
        library.borrowBook("987654321", "MEM102");
        assert library.getBookList().stream()
                .filter(b -> !b.isAvailable())
                .anyMatch(b -> b.getISBN().equals("987654321")) : "Active Loans Test Failed!";
        System.out.println("Active Loans Test Passed.");
    }
}
