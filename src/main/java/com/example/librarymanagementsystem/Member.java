package com.example.librarymanagementsystem;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {
    private String name;
    private String memberID;
    private ArrayList<String> borrowedBooks;
    private static final int BORROW_LIMIT = 3; // Limit on books a member can borrow

    public Member(String name, String memberID) {
        this.name = name;
        this.memberID = memberID;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getMemberID() {
        return memberID;
    }

    public ArrayList<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean canBorrow() {
        return borrowedBooks.size() < BORROW_LIMIT;
    }

    public void borrowBook(String ISBN) {
        if (canBorrow()) {
            borrowedBooks.add(ISBN);
        }
    }

    public void returnBook(String ISBN) {
        borrowedBooks.remove(ISBN);
    }

    @Override
    public String toString() {
        return "Member{" +
                "Name='" + name + '\'' +
                ", MemberID='" + memberID + '\'' +
                ", BorrowedBooks=" + borrowedBooks +
                '}';
    }
}

