package com.example.librarymanagementsystem;

import java.io.Serializable;
import java.time.LocalDate;

// Represents a book in the library and implements Serializable for data persistence across our program
public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable; // true if book is available, false if borrowed
    private String borrowerID; // ID of the member who borrowed the book
    private LocalDate borrowedDate; // The date the book was borrowed
    private LocalDate dueDate; // The due date for returning the book


    // Constructor to initialize a book with a title, author, and ISBN
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true;
        this.borrowerID = null;
        this.borrowedDate = null;
        this.dueDate = null;
    }

    // Getters and Setters for a Book object
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    // Sets the borrowed date and calculates the due date based on it. If the borrowed date is null, both dates are reset.
    public void setBorrowedDate(LocalDate borrowedDate) {
        if (borrowedDate == null) {
            this.borrowedDate = null;
            this.dueDate = null;
        } else {
            this.borrowedDate = borrowedDate;
            this.dueDate = borrowedDate.plusWeeks(1); // Calculate due date only if borrowedDate is not null
        }
    }


    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    // Checks if the book is overdue by comparing the due date with the current date
    public boolean isOverdue() {
        if (dueDate == null) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }

    // Formats the book details as a string for display purposes in the app
    @Override
    public String toString() {
        return "Book{" +
                "Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", Available=" + isAvailable +
                ", BorrowerID='" + borrowerID + '\'' +
                ", BorrowedDate=" + borrowedDate +
                ", DueDate=" + dueDate +
                '}';
    }
}