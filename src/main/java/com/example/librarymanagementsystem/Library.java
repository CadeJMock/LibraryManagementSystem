package com.example.librarymanagementsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Library {
    private ArrayList<Book> bookList;
    private ArrayList<Member> memberList;

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

    // Methods to add/remove books and members
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

    // Borrow and return books
    public boolean borrowBook(String ISBN, String memberID) {
        for (Book book : bookList) {
            if (book.getISBN().equals(ISBN) && book.isAvailable()) {
                for (Member member : memberList) {
                    if (member.getMemberID().equals(memberID) && member.canBorrow()) {
                        book.setAvailable(false);
                        book.setBorrowerID(memberID);
                        book.setBorrowedDate(LocalDate.now());
                        member.borrowBook(ISBN);
                        return true;
                    }
                }
            }
        }
        return false; // Borrowing failed
    }


    public boolean returnBook(String ISBN, String memberID) {
        for (Book book : bookList) {
            if (book.getISBN().equals(ISBN) && !book.isAvailable()) {
                book.setAvailable(true);
                String borrowerID = book.getBorrowerID();
                book.setBorrowerID(null);
                book.setBorrowedDate(null);
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


    // Search Books
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

    // Library Statistics
    public int totalBooks() {
        return bookList.size();
    }

    public int totalMembers() {
        return memberList.size();
    }

    public String mostBorrowedBook() {
        return bookList.stream()
                .max(Comparator.comparingInt(book -> (int) memberList.stream()
                        .filter(member -> member.getBorrowedBooks().contains(book.getISBN()))
                        .count()))
                .map(Book::getTitle)
                .orElse("No books borrowed yet.");
    }

    // File I/O for persistence
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            oos.writeObject(bookList);
            oos.writeObject(memberList);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library_data.dat"))) {
            bookList = (ArrayList<Book>) ois.readObject();
            memberList = (ArrayList<Member>) ois.readObject();
        }
    }
}

