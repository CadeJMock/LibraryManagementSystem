package com.example.librarymanagementsystem;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Test class to validate the functionality of the LMS application. Also generates fake data to "library_data.dat" for testing purposes.
public class LibraryTest {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static List<String> failedTests = new ArrayList<>(); // Tracks the names of the failed tests

    public static void main(String[] args) {
        Library library = new Library();

        // Check if library data file exists, then either load or populate the data.
        File dataFile = new File("library_data.dat");
        if (!dataFile.exists()) {
            System.out.println("No existing data found. Creating and populating library_data.dat...");
            populateDefaultLibraryData(library); // Add default books and members
            try {
                library.saveData(); // Save the data to file.
                System.out.println("library_data.dat created and populated successfully.");
            } catch (Exception e) {
                System.out.println("Failed to save library data: " + e.getMessage());
            }
        } else {
            try {
                library.loadData(); // Load data from file
                System.out.println("Library data loaded successfully.");
            } catch (Exception e) {
                System.out.println("Failed to load library data: " + e.getMessage());
            }
        }

        // Run all tests
        System.out.println("\nRunning Tests...");

        testAddBook(library);
        testAddMember(library);
        testBorrowBook(library);
        testReturnBook(library);
        testDuplicateISBN(library);
        testDuplicateMemberID(library);
        testBorrowLimit(library);
        testOverdueLoans(library);
        testDeleteBook(library);
        testDeleteMember(library);
        testSearchBooks(library);

        // Display test results summary
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

    // Runs an individual test and records its results
    private static void runTest(String testName, Runnable test) {
        totalTests++;
        try {
            test.run(); // Execute the test
            passedTests++; // Increment passed tests if successful
            System.out.println(testName + " Test Passed.");
        } catch (AssertionError e) {
            failedTests.add(testName); // Record failed test
            System.out.println(testName + " Test Failed: " + e.getMessage());
        }
    }

    // Populates the library with 10 books, 10 members, and some overdue loans
    private static void populateDefaultLibraryData(Library library) {
        library.addBook(new Book("Book 1", "Author 1", "111111111"));
        library.addBook(new Book("Book 2", "Author 2", "222222222"));
        library.addBook(new Book("Book 3", "Author 3", "333333333"));
        library.addBook(new Book("Book 4", "Author 4", "444444444"));
        library.addBook(new Book("Book 5", "Author 5", "555555555"));
        library.addBook(new Book("Book 6", "Author 6", "666666666"));
        library.addBook(new Book("Book 7", "Author 7", "777777777"));
        library.addBook(new Book("Book 8", "Author 8", "888888888"));
        library.addBook(new Book("Book 9", "Author 9", "999999999"));
        library.addBook(new Book("Book 10", "Author 10", "101010101"));

        library.addMember(new Member("Member 1", "MEM001"));
        library.addMember(new Member("Member 2", "MEM002"));
        library.addMember(new Member("Member 3", "MEM003"));
        library.addMember(new Member("Member 4", "MEM004"));
        library.addMember(new Member("Member 5", "MEM005"));
        library.addMember(new Member("Member 6", "MEM006"));
        library.addMember(new Member("Member 7", "MEM007"));
        library.addMember(new Member("Member 8", "MEM008"));
        library.addMember(new Member("Member 9", "MEM009"));
        library.addMember(new Member("Member 10", "MEM010"));

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

    // Tests adding a book to the library
    private static void testAddBook(Library library) {
        runTest("Add Book", () -> {
            library.addBook(new Book("Test Book", "Test Author", "123456789"));
            assert library.getBookList().stream().anyMatch(book -> book.getISBN().equals("123456789"));
        });
    }

    // Tests adding a member to the library
    private static void testAddMember(Library library) {
        runTest("Add Member", () -> {
            library.addMember(new Member("Test Member", "MEM101"));
            assert library.getMemberList().stream().anyMatch(member -> member.getMemberID().equals("MEM101"));
        });
    }

    // Tests borrowing a book
    private static void testBorrowBook(Library library) {
        runTest("Borrow Book", () -> {
            boolean success = library.borrowBook("333333333", "MEM003");
            assert success;
            assert library.getBookList().stream().anyMatch(book -> book.getISBN().equals("333333333") && !book.isAvailable());
        });
    }

    // Tests returning a book
    private static void testReturnBook(Library library) {
        runTest("Return Book", () -> {
            boolean success = library.returnBook("333333333", "MEM003");
            assert success;
            assert library.getBookList().stream().anyMatch(book -> book.getISBN().equals("333333333") && book.isAvailable());
        });
    }

    // Tests adding a duplicate ISBN to the library
    private static void testDuplicateISBN(Library library) {
        runTest("Duplicate ISBN", () -> {
            library.addBook(new Book("Duplicate Book", "Duplicate Author", "111111111"));
            long count = library.getBookList().stream().filter(book -> book.getISBN().equals("111111111")).count();
            assert count == 1;
        });
    }

    // Tests adding a duplicate member ID to the library
    private static void testDuplicateMemberID(Library library) {
        runTest("Duplicate Member ID", () -> {
            library.addMember(new Member("Duplicate Member", "MEM001"));
            long count = library.getMemberList().stream().filter(member -> member.getMemberID().equals("MEM001")).count();
            assert count == 1;
        });
    }

    // Tests enforcing the borrowing limit (3)
    private static void testBorrowLimit(Library library) {
        runTest("Borrow Limit", () -> {
            Member member = new Member("Limit Tester", "MEMLIMIT");
            library.addMember(member);
            library.addBook(new Book("Limit Book 1", "Author", "LIM1"));
            library.addBook(new Book("Limit Book 2", "Author", "LIM2"));
            library.addBook(new Book("Limit Book 3", "Author", "LIM3"));
            library.addBook(new Book("Limit Book 4", "Author", "LIM4"));

            assert library.borrowBook("LIM1", "MEMLIMIT");
            assert library.borrowBook("LIM2", "MEMLIMIT");
            assert library.borrowBook("LIM3", "MEMLIMIT");
            assert !library.borrowBook("LIM4", "MEMLIMIT");
        });
    }

    // Tests identifying overdue loans
    private static void testOverdueLoans(Library library) {
        runTest("Overdue Loans", () -> {
            Book book = library.getBookList().stream().filter(b -> b.getISBN().equals("111111111")).findFirst().orElse(null);
            assert book != null;
            assert book.isOverdue();
        });
    }

    // Tests deleting a book from the library
    private static void testDeleteBook(Library library) {
        runTest("Delete Book", () -> {
            library.removeBook("123456789");
            assert library.getBookList().stream().noneMatch(book -> book.getISBN().equals("123456789"));
        });
    }

    // Tests deleting a member from the library
    private static void testDeleteMember(Library library) {
        runTest("Delete Member", () -> {
            library.removeMember("MEM101");
            assert library.getMemberList().stream().noneMatch(member -> member.getMemberID().equals("MEM101"));
        });
    }

    // Tests searching for a book in the library
    private static void testSearchBooks(Library library) {
        runTest("Search Books", () -> {
            List<Book> results = library.searchBooks("Book 1");
            assert results.size() > 0;
            assert results.get(0).getTitle().contains("Book 1");
        });
    }
}
