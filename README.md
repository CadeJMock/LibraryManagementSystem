# Library Management System

This is the final project for my **CSCI-428 Course** at **East Texas A&M**.  
Author: **Cade Mock**  
CWID: **50350556**  
Email: **cmock2@leomail.tamuc.edu**

---

## Problem Statement

Libraries are essential institutions for fostering knowledge and education. However, managing a library’s operations efficiently can be challenging, especially when handling book inventories, members, and active loans manually. The need for a streamlined system arises to automate these tasks, reducing errors and improving the user experience for both librarians and library members. This Library Management System addresses these challenges by providing an intuitive JavaFX-based application with robust functionality to manage books, members, and loans.

---

## Features

### 1. **Add Books and Members**
- Easily add new books by providing the title, author, and ISBN.
- Ensure unique ISBN validation to prevent duplicates.
- Add members by entering their name and Member ID.
- Enforce unique Member ID validation to avoid conflicts.

### 2. **Borrow and Return Books**
- Borrow books by selecting an available book and linking it to a member.
- Limit borrowing to a maximum of 3 books per member.
- Return books by selecting from the list of currently checked-out books.

### 3. **Search and View Details**
- Search for books by title, author, or ISBN.
- View detailed information about books, members, and active loans.
- Overdue books are highlighted in red for easier tracking.

### 4. **Manage Active Loans**
- View a list of all active loans, including borrower details.
- Delete loans with automatic updates to both book and member statuses.

### 5. **Delete Books and Members**
- Delete books or members with automatic checks:
  - If a book is currently borrowed, it will be returned before deletion.
  - If a member has active loans, their books will be returned automatically.

### 6. **Persistence and Testing**
- Library data is saved to `library_data.dat` to ensure continuity between sessions.
- Comprehensive test cases validate the application's functionality, with console feedback on test results.

---

## How to Use

1. **Run the Application**  
   Execute `LibraryApp.java` to start the Library Management System with its JavaFX interface.

2. **Main Menu**  
   The main menu categorizes actions into:
   - **Add Features**: Add books and members.
   - **Borrow/Return**: Borrow and return books.
   - **View Features**: View books, members, and active loans.
   - **Save and Exit**: Save library data and exit the application.

3. **Adding Entries**  
   - Navigate to "Add Book" or "Add Member" to create new entries.
   - Ensure ISBNs and Member IDs are unique.

4. **Borrow/Return Books**  
   - Select "Borrow Book" or "Return Book" to manage loans.
   - Search for books or members using their details.
   - Overdue loans are highlighted in red for easier identification.

5. **Managing Entries**  
   - Use the "View" sections to manage books, members, and active loans.
   - Right-click or double-click entries to view details or delete them.

6. **Testing**  
   - Run `LibraryTest.java` to validate functionality and generate the initial `library_data.dat` file if it doesn’t exist with fake data for testing purposes.
   - Test results are displayed in the console, including the number of tests passed and failed.

---

## File Structure

- **Book.java**: Represents individual book objects with details such as title, author, ISBN, availability, and due date.
- **Member.java**: Represents library members, including their borrowed books and borrowing limits.
- **Library.java**: Core logic for managing books, members, and loans, including file persistence.
- **LibraryApp.java**: The JavaFX interface for interacting with the Library Management System.
- **LibraryTest.java**: Automated test suite to validate all features and functionality.

---
