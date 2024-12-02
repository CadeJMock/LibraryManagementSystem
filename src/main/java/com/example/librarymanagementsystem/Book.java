/*
 * Cade Mock
 * CWID: 50350556
 * Date (Last Updated) : 12/1/2024
 * Email: cmock2@leomail.tamuc.edu
 */

package com.example.librarymanagementsystem;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a book in the library system.
 * Implements Serializable to enable data persistence by saving and loading book information.
 *
 * Each book has the following attributes:
 * - Title: The title of the book.
 * - Author: The author of the book.
 * - ISBN: A unique identifier for the book.
 * - Availability: Tracks whether the book is available for borrowing.
 * - Borrower ID: The ID of the member who borrowed the book (if applicable).
 * - Borrowed Date: The date the book was borrowed (if applicable).
 * - Due Date: The date the book is due to be returned (if applicable).
 *
 * This class is used to manage and track the state of books in the library system.
 */
public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable; // true if book is available, false if borrowed
    private String borrowerID; // ID of the member who borrowed the book
    private LocalDate borrowedDate; // The date the book was borrowed
    private LocalDate dueDate; // The due date for returning the book


    /**
     * Constructor to initialize a book with a title, author, and ISBN.
     * Sets the initial state of the book as available and resets borrower-related fields.
     *
     * @param title  The title of the book.
     * @param author The author of the book.
     * @param ISBN   The ISBN of the book.
     */
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true; // available by default
        this.borrowerID = null; // no borrower initially
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

    /**
     * Sets the borrowed date for the book and calculates the due date based on it.
     * If the borrowed date is set to null, both the borrowed and due dates are reset.
     *
     * @param borrowedDate The date when the book is borrowed. If null, the dates are cleared.
     */
    public void setBorrowedDate(LocalDate borrowedDate) {
        if (borrowedDate == null) {
            // clear both borrowedDate and dueDate when borrowedDate is null
            this.borrowedDate = null;
            this.dueDate = null;
        } else {
            // set the borrowed date
            this.borrowedDate = borrowedDate;
            // set the due date as one week after the borrowed date
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