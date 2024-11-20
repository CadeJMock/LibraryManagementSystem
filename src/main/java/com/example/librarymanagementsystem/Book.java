package com.example.librarymanagementsystem;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;
    private String borrowerID;
    private LocalDate borrowedDate;
    private LocalDate dueDate;


    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = true;
        this.borrowerID = null;
        this.borrowedDate = null;
        this.dueDate = null;
    }

    // Getters and Setters
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

    public boolean isOverdue() {
        if (dueDate == null) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }

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
