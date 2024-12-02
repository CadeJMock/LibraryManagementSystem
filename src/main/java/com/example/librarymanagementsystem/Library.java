/*
 * Cade Mock
 * CWID: 50350556
 * Date (Last Updated) : 12/1/2024
 * Email: cmock2@leomail.tamuc.edu
 *
 * This class interacts with the Book and Member class to create a functional Library object with members and books.
 */

package com.example.librarymanagementsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

// Represents the library, managing books and members, and implements serializable for data persistence across the program *unsure if I need serializable for this
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

    // Allows a member to borrow a book, updating both the book and member records
    public boolean borrowBook(String ISBN, String memberID) {
        for (Book book : bookList) {
            if (book.getISBN().equals(ISBN) && book.isAvailable()) {
                for (Member member : memberList) {
                    if (member.getMemberID().equals(memberID) && member.canBorrow()) {
                        book.setAvailable(false);
                        book.setBorrowerID(memberID);
                        book.setBorrowedDate(LocalDate.now()); // Set borrowedDate here
                        member.borrowBook(ISBN);
                        return true;
                    }
                }
            }
        }
        return false; // Borrowing failed
    }

    // Allows a member to return a book, updating both the book and member records
    public boolean returnBook(String ISBN, String memberID) {
        for (Book book : bookList) {
            if (book.getISBN().equals(ISBN) && !book.isAvailable()) {
                book.setAvailable(true);
                String borrowerID = book.getBorrowerID();
                book.setBorrowerID(null);
                book.setBorrowedDate(null); // Clear borrowedDate safely
                book.setDueDate(null); // Clear dueDate
                for (Member member : memberList) {
                    if (member.getMemberID().equals(borrowerID)) {
                        member.returnBook(ISBN);
                        return true;
                    }
                }
            }
        }
        return false; // Return failed
    }





    // Searches for books based on title, author, or ISBN
    public ArrayList<Book> searchBooks(String query) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getISBN().equals(query)) {
                results.add(book);
            }
        }
        return results;
    }

    // File I/O for persistence. Saves the library's data to a file, only called if the user Saves & Exists (bottom right) instead of closing the window
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            oos.writeObject(bookList);
            oos.writeObject(memberList);
        }
    }

    // Loads the library's data from a file (library_data.dat). This file can be generated for testing purposes by running LibraryTest.java
    @SuppressWarnings("unchecked")
    public void loadData() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library_data.dat"))) {
            bookList = (ArrayList<Book>) ois.readObject();
            memberList = (ArrayList<Member>) ois.readObject();
        }
    }
}