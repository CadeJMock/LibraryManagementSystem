package com.example.librarymanagementsystem;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryTest {

    // Track test results
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static List<String> failedTests = new ArrayList<>();

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

        // Display test results
        System.out.println("\n--- Test Results ---");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed Tests: " + passedTests);
        System.out.println("Failed Tests: " + (totalTests - passedTests));
        if (!failedTests.isEmpty()) {
            System.out.println("\nFailed Test Names:");
            for (String testName : failedTests) {
                System.out.println("- " + testName);
            }
        }
        System.out.println("---------------------");
    }

    private static void runTests(Library library) {
        runTest("Add Book", () -> testAddBook(library));
        runTest("Add Member", () -> testAddMember(library));
        runTest("Borrow Book", () -> testBorrowBook(library));
        runTest("Return Book", () -> testReturnBook(library));
        runTest("Duplicate ISBN", () -> testDuplicateISBN(library));
        runTest("Duplicate Member ID", () -> testDuplicateMemberID(library));
        runTest("Delete Book", () -> testDeleteBook(library));
        runTest("Delete Member", () -> testDeleteMember(library));
        runTest("Active Loans", () -> testActiveLoans(library));
    }

    private static void runTest(String testName, Runnable test) {
        totalTests++;
        try {
            test.run();
            passedTests++;
            System.out.println(testName + " Test Passed.");
        } catch (AssertionError e) {
            failedTests.add(testName);
            System.out.println(testName + " Test Failed: " + e.getMessage());
        }
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

        // Add overdue loans
        library.borrowBook("111111111", "MEM001");
        library.getBookList().stream()
                .filter(book -> book.getISBN().equals("111111111"))
                .findFirst()
                .ifPresent(book -> book.setBorrowedDate(LocalDate.now().minusWeeks(2)));

        library.borrowBook("222222222", "MEM002");
        library.getBookList().stream()
                .filter(book -> book.getISBN().equals("222222222"))
                .findFirst()
                .ifPresent(book -> book.setBorrowedDate(LocalDate.now().minusWeeks(3)));
    }

    private static void testAddBook(Library library) {
        Book book = new Book("Test Title", "Test Author", "123456789");
        library.addBook(book);
        assert library.getBookList().contains(book) : "Add Book Failed!";
    }

    private static void testAddMember(Library library) {
        Member member = new Member("Test Member", "MEM101");
        library.addMember(member);
        assert library.getMemberList().contains(member) : "Add Member Failed!";
    }

    private static void testBorrowBook(Library library) {
        String isbn = "111111111";
        String memberId = "MEM001";
        library.borrowBook(isbn, memberId);
        Book borrowedBook = library.getBookList().stream()
                .filter(book -> book.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
        assert borrowedBook != null && !borrowedBook.isAvailable() : "Borrow Book Failed!";
    }

    private static void testReturnBook(Library library) {
        String isbn = "111111111";
        String memberId = "MEM001";
        library.returnBook(isbn, memberId);
        Book returnedBook = library.getBookList().stream()
                .filter(book -> book.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
        assert returnedBook != null && returnedBook.isAvailable() : "Return Book Failed!";
    }

    private static void testDuplicateISBN(Library library) {
        Book duplicateBook = new Book("Duplicate Title", "Duplicate Author", "111111111");
        library.addBook(duplicateBook);
        long count = library.getBookList().stream()
                .filter(book -> book.getISBN().equals("111111111"))
                .count();
        assert count == 1 : "Duplicate ISBN Test Failed!";
    }

    private static void testDuplicateMemberID(Library library) {
        Member duplicateMember = new Member("Duplicate Member", "MEM001");
        library.addMember(duplicateMember);
        long count = library.getMemberList().stream()
                .filter(member -> member.getMemberID().equals("MEM001"))
                .count();
        assert count == 1 : "Duplicate Member ID Test Failed!";
    }

    private static void testDeleteBook(Library library) {
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
    }

    private static void testDeleteMember(Library library) {
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
    }

    private static void testActiveLoans(Library library) {
        Book book = new Book("Loan Test Title", "Loan Test Author", "987654321");
        Member member = new Member("Loan Test Member", "MEM102");
        library.addBook(book);
        library.addMember(member);
        library.borrowBook("987654321", "MEM102");
        assert library.getBookList().stream()
                .filter(b -> !b.isAvailable())
                .anyMatch(b -> b.getISBN().equals("987654321")) : "Active Loans Test Failed!";
    }
}
