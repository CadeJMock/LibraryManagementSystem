package com.example.librarymanagementsystem;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {
    private String name;
    private String memberID;
    private ArrayList<String> borrowedBooks;

    private static final int BORROW_LIMIT = 3; // Limit on books a member can borrow

    // Constructor to initialize a member with a name and ID
    public Member(String name, String memberID) {
        this.name = name;
        this.memberID = memberID;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters for a Member object
    public String getName() {
        return name;
    }

    public String getMemberID() {
        return memberID;
    }

    public ArrayList<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Checks if the member can borrow more books based on the borrow limit, see BORROW_LIMIT
    public boolean canBorrow() {
        return borrowedBooks.size() < BORROW_LIMIT;
    }

    // Adds a book to the member's borrowed list
    public void borrowBook(String ISBN) {
        if (canBorrow()) {
            if (!borrowedBooks.contains(ISBN)) {
                borrowedBooks.add(ISBN);
            }
        }
    }

    // Removes a book from the member's borrowed list when it is returned
    public void returnBook(String ISBN) {
        borrowedBooks.remove(ISBN); // Remove ISBN from borrowedBooks
    }


    // Formats member details as a string for display purposes on the app
    @Override
    public String toString() {
        return "Member{" +
                "Name='" + name + '\'' +
                ", MemberID='" + memberID + '\'' +
                ", BorrowedBooks=" + borrowedBooks +
                '}';
    }
}