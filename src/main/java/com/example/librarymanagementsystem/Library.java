/*
 * Cade Mock
 * CWID: 50350556
 * Date (Last Updated) : 12/1/2024
 * Email: cmock2@leomail.tamuc.edu
 */

package com.example.librarymanagementsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents the library system, managing books, members, and their interactions.
 * Implements Serializable to enable data persistence for saving and loading the entire library state.
 *
 * Responsibilities of the Library class include:
 * - Managing a collection of books and members.
 * - Handling operations like borrowing and returning books.
 * - Providing search functionality for books and members.
 * - Facilitating data persistence through saving and loading operations.
 *
 * Note: Unsure if Serializable is needed here
 */
public class Library implements Serializable {
    private ArrayList<Book> bookList; // List of all books in the library
    private ArrayList<Member> memberList; // List of all members in the library

    // Library constructor with a book list and member list
    public Library() {
        this.bookList = new ArrayList<>();
        this.memberList = new ArrayList<>();
    }

    // getters for bookList and memberList
    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }

    // Methods to add/remove books and members from the library
    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(String ISBN) {
        bookList.removeIf(book -> book.getISBN().equals(ISBN));
    }

    public void addMember(Member member) {
        memberList.add(member);
    }

    public void removeMember(String memberID) {
        memberList.removeIf(member -> member.getMemberID().equals(memberID));
    }

    /**
     * Allows a member to borrow a book if both the book and member meet the required conditions.
     *
     * @param ISBN      The ISBN of the book to be borrowed.
     * @param memberID  The ID of the member borrowing the book.
     * @return          True if the borrowing process is successful, false otherwise.
     */
    public boolean borrowBook(String ISBN, String memberID) {
        for (Book book : bookList) { // Iterate through the lists of books to find the book with the given ISBN & ensure it is available
            if (book.getISBN().equals(ISBN) && book.isAvailable()) {
                for (Member member : memberList) { // Iterate through the list of members to find the member with the given ID and check if they can borrow more books
                    if (member.getMemberID().equals(memberID) && member.canBorrow()) {
                        // update attributes of the book to reflect that it is borrowed
                        book.setAvailable(false);
                        book.setBorrowerID(memberID);
                        book.setBorrowedDate(LocalDate.now());
                        member.borrowBook(ISBN); // add the book to the member's list of borrowed books
                        return true;
                    }
                }
            }
        }
        return false; // Borrowing failed
    }

    /**
     * Allows a member to return a book, updating both the book and member records.
     *
     * @param ISBN      The ISBN of the book being returned.
     * @param memberID  The ID of the member returning the book.
     * @return          True if the return process is successful, false otherwise.
     */
    public boolean returnBook(String ISBN, String memberID) {
        for (Book book : bookList) { // Iterate through the list of books to find the book with the given ISBN and ensure it is available
            if (book.getISBN().equals(ISBN) && !book.isAvailable()) {
                // update attributes of the book to reflect that it is no longer borrowed (available)
                book.setAvailable(true);
                String borrowerID = book.getBorrowerID();
                book.setBorrowerID(null);
                book.setBorrowedDate(null);
                book.setDueDate(null);
                for (Member member : memberList) { // Iterate through the list of members and find the one who borrowed this book
                    if (member.getMemberID().equals(borrowerID)) {
                        member.returnBook(ISBN); // call returnBook to remove the book from the member's list of borrowed books
                        return true;
                    }
                }
            }
        }
        return false; // Return failed
    }





    /**
     * Searches for books in the library based on a query string.
     * The query is matched against the book's title, author, or ISBN.
     *
     * @param query  The search string to look for in the book details.
     * @return       A list of books that match the search query.
     */
    public ArrayList<Book> searchBooks(String query) {
        ArrayList<Book> results = new ArrayList<>(); // Initialize an empty list to store each search result
        for (Book book : bookList) { // Iterate through the list of books in the library
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) || // check if the query matches the book's title (ignore cases)
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) || // check if the query matches the book's author (ignore cases)
                    book.getISBN().equals(query)) { // check if the query matches the book's ISBN (exact match)
                results.add(book); // add the matching book to the results list
            }
        }
        return results; // return the list of matching books
    }

    /**
     * Saves the library's data (books and members) to a file for persistence.
     * This method is called only when the user selects "Save & Exit" instead of closing the window directly.
     *
     * @throws IOException  If an I/O error occurs during file operations.
     */
    public void saveData() throws IOException {
        // Use a try-with statement that can ensure the ObjectOutputStream is closed automatically
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            oos.writeObject(bookList); // write the list of books to the file
            oos.writeObject(memberList); // write the list of members to the file
        }
    }

    /**
     * Loads the library's data (books and members) from the file `library_data.dat`.
     * This allows the library to restore its previous state upon application startup.
     * If the file does not exist or contains invalid data, this method will throw an exception.
     *
     * Note: The file can be generated for testing purposes by running `LibraryTest.java`.
     *
     * @throws IOException            If an I/O error occurs during file operations.
     * @throws ClassNotFoundException If the objects in the file cannot be deserialized.
     */
    @SuppressWarnings("unchecked") // Suppress the warnings related to type casting #annoying
    public void loadData() throws IOException, ClassNotFoundException {
        // try-with statement to ensure the ObjectInputStream closes automatically
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library_data.dat"))) {
            bookList = (ArrayList<Book>) ois.readObject(); // read and deserialize the list of books from the file
            memberList = (ArrayList<Member>) ois.readObject(); // read and deserialize the list of members from the file
        }
    }
}