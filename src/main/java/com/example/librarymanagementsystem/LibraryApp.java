package com.example.librarymanagementsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LibraryApp extends Application {
    private Library library = new Library();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        // Load data on startup
        try {
            library.loadData();
        } catch (Exception e) {
            System.out.println("No existing data found. Starting fresh.");
        }

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        // Main menu
        Label header = new Label("Library Management System");
        Button addBookButton = new Button("Add Book");
        Button addMemberButton = new Button("Add Member");
        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button viewBooksButton = new Button("View Books");
        Button viewMembersButton = new Button("View Members");
        Button searchBooksButton = new Button("Search Books");
        Button viewActiveLoansButton = new Button("View Active Loans");
        Button saveExitButton = new Button("Save and Exit");

        // Event handlers for buttons
        addBookButton.setOnAction(e -> addBook());
        addMemberButton.setOnAction(e -> addMember());
        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
        viewBooksButton.setOnAction(e -> viewBooks());
        viewMembersButton.setOnAction(e -> viewMembers());
        viewActiveLoansButton.setOnAction(e -> viewActiveLoans());
        searchBooksButton.setOnAction(e -> searchBooks());
        saveExitButton.setOnAction(e -> {
            try {
                library.saveData();
                System.exit(0);
            } catch (Exception ex) {
                System.out.println("Failed to save data: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(header, addBookButton, addMemberButton, borrowBookButton, returnBookButton,
                viewBooksButton, viewMembersButton, searchBooksButton, viewActiveLoansButton, saveExitButton);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBook() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Add Book");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        TextField titleField = new TextField();
        titleField.setPromptText("Enter Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Enter Author");

        TextField isbnField = new TextField();
        isbnField.setPromptText("Enter ISBN");

        Button addButton = new Button("Add Book");
        addButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                showAlert("Error", "All fields must be filled!");
            } else {
                library.addBook(new Book(title, author, isbn));
                showAlert("Success", "Book added successfully!");
                addBookStage.close();
            }
        });

        layout.getChildren().addAll(
                new Label("Add a New Book"),
                titleField, authorField, isbnField,
                addButton
        );

        Scene scene = new Scene(layout, 300, 200);
        addBookStage.setScene(scene);
        addBookStage.show();
    }


    private void addMember() {
        Stage addMemberStage = new Stage();
        addMemberStage.setTitle("Add Member");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");

        TextField memberIdField = new TextField();
        memberIdField.setPromptText("Enter Member ID");

        Button addButton = new Button("Add Member");
        addButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String memberId = memberIdField.getText();

            if (name.isEmpty() || memberId.isEmpty()) {
                showAlert("Error", "All fields must be filled!");
            } else {
                library.addMember(new Member(name, memberId));
                showAlert("Success", "Member added successfully!");
                addMemberStage.close();
            }
        });

        layout.getChildren().addAll(
                new Label("Add a New Member"),
                nameField, memberIdField,
                addButton
        );

        Scene scene = new Scene(layout, 300, 150);
        addMemberStage.setScene(scene);
        addMemberStage.show();
    }


    private void borrowBook() {
        Stage borrowBookStage = new Stage();
        borrowBookStage.setTitle("Borrow Book");

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-padding: 15;");

        // Book search and selection
        Label bookSearchLabel = new Label("Search for a Book");
        TextField bookSearchField = new TextField();
        bookSearchField.setPromptText("Search by Title, Author, or ISBN");
        ListView<Book> bookListView = new ListView<>();
        TextField isbnField = new TextField(); // Declare isbnField
        isbnField.setPromptText("Enter Book ISBN");

        // Populate list with available books
        updateBookList(bookListView, library.getBookList().stream()
                .filter(Book::isAvailable)
                .toList());

        // Update book list based on search
        bookSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBookList(bookListView, library.getBookList().stream()
                    .filter(book -> book.isAvailable() &&
                            (book.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                                    book.getAuthor().toLowerCase().contains(newValue.toLowerCase()) ||
                                    book.getISBN().toLowerCase().contains(newValue.toLowerCase())))
                    .toList());
        });

        // Populate ISBN field when book is selected
        bookListView.setOnMouseClicked(event -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                isbnField.setText(selectedBook.getISBN());
            }
        });

        Label isbnLabel = new Label("Book ISBN *");
        isbnLabel.setStyle("-fx-text-fill: red;"); // Highlight required field

        // Member search and selection
        Label memberSearchLabel = new Label("Search for a Member");
        TextField memberSearchField = new TextField();
        memberSearchField.setPromptText("Search by Name or Member ID");
        ListView<Member> memberListView = new ListView<>();
        TextField memberIdField = new TextField(); // Declare memberIdField
        memberIdField.setPromptText("Enter Member ID");

        // Populate list with all members
        updateMemberList(memberListView, library.getMemberList());

        // Update member list based on search
        memberSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateMemberList(memberListView, library.getMemberList().stream()
                    .filter(member -> member.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                            member.getMemberID().toLowerCase().contains(newValue.toLowerCase()))
                    .toList());
        });

        // Populate Member ID field when member is selected
        memberListView.setOnMouseClicked(event -> {
            Member selectedMember = memberListView.getSelectionModel().getSelectedItem();
            if (selectedMember != null) {
                memberIdField.setText(selectedMember.getMemberID());
            }
        });

        Label memberIdLabel = new Label("Member ID *");
        memberIdLabel.setStyle("-fx-text-fill: red;"); // Highlight required field

        // Borrow button
        Button borrowButton = new Button("Borrow Book");
        borrowButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        borrowButton.setOnAction(e -> {
            String isbn = isbnField.getText();
            String memberId = memberIdField.getText();

            if (isbn.isEmpty() || memberId.isEmpty()) {
                showAlert("Error", "Both fields must be filled!");
                return;
            }

            boolean success = library.borrowBook(isbn, memberId);
            if (success) {
                showAlert("Success", "Book borrowed successfully!");
                borrowBookStage.close();
            } else {
                showAlert("Error", "Borrowing failed! Make sure the book is available and the member exists.");
            }
        });

        // Organize layout into sections
        mainLayout.getChildren().addAll(
                bookSearchLabel, bookSearchField, bookListView, isbnLabel, isbnField,
                memberSearchLabel, memberSearchField, memberListView, memberIdLabel, memberIdField,
                borrowButton
        );

        Scene scene = new Scene(mainLayout, 500, 700);
        borrowBookStage.setScene(scene);
        borrowBookStage.show();
    }




    private void returnBook() {
        Stage returnBookStage = new Stage();
        returnBookStage.setTitle("Return Book");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");

        Label bookSearchLabel = new Label("Search for a Book");
        TextField bookSearchField = new TextField();
        bookSearchField.setPromptText("Search by Title, Author, or ISBN");

        ListView<Book> bookListView = new ListView<>();
        TextField isbnField = new TextField(); // Declare isbnField
        isbnField.setPromptText("Enter Book ISBN");

        updateBookList(bookListView, library.getBookList().stream()
                .filter(book -> !book.isAvailable())
                .toList());

        bookSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBookList(bookListView, library.getBookList().stream()
                    .filter(book -> !book.isAvailable() &&
                            (book.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                                    book.getAuthor().toLowerCase().contains(newValue.toLowerCase()) ||
                                    book.getISBN().toLowerCase().contains(newValue.toLowerCase())))
                    .toList());
        });

        bookListView.setOnMouseClicked(event -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                isbnField.setText(selectedBook.getISBN());
            }
        });

        Label isbnLabel = new Label("Book ISBN *");
        isbnLabel.setStyle("-fx-text-fill: red;"); // Highlight required field

        Button returnButton = new Button("Return Book");
        returnButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button
        returnButton.setOnAction(e -> {
            String isbn = isbnField.getText();

            if (isbn.isEmpty()) {
                showAlert("Error", "ISBN field must be filled!");
                return;
            }

            boolean success = library.returnBook(isbn, null);
            if (success) {
                showAlert("Success", "Book returned successfully!");
                returnBookStage.close();
            } else {
                showAlert("Error", "Return failed! Make sure the book exists and is checked out.");
            }
        });

        layout.getChildren().addAll(
                bookSearchLabel, bookSearchField, bookListView, isbnLabel, isbnField,
                returnButton
        );

        Scene scene = new Scene(layout, 400, 500);
        returnBookStage.setScene(scene);
        returnBookStage.show();
    }


    private void updateBookList(ListView<Book> listView, List<Book> books) {
        listView.getItems().clear();
        listView.getItems().addAll(books);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle() + " (ISBN: " + book.getISBN() + ")");
                }
            }
        });
    }

    private void updateMemberList(ListView<Member> listView, List<Member> members) {
        listView.getItems().clear();
        listView.getItems().addAll(members);
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Member member, boolean empty) {
                super.updateItem(member, empty);
                if (empty || member == null) {
                    setText(null);
                } else {
                    setText(member.getName() + " (ID: " + member.getMemberID() + ")");
                }
            }
        });
    }



    private void viewBooks() {
        Stage viewBooksStage = new Stage();
        viewBooksStage.setTitle("View Books");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        ListView<Book> listView = new ListView<>();
        listView.getItems().addAll(library.getBookList()); // Add all books to the ListView
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getISBN() + ")");
                }
            }
        });

        // Double-click event for viewing detailed book info
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Book selectedBook = listView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    showBookDetails(selectedBook);
                }
            }
        });

        layout.getChildren().addAll(new Label("Books in Library:"), listView);
        Scene scene = new Scene(layout, 400, 400);
        viewBooksStage.setScene(scene);
        viewBooksStage.show();
    }

    // Mini-window to show book details
    private void showBookDetails(Book book) {
        Stage bookDetailsStage = new Stage();
        bookDetailsStage.setTitle("Book Details");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        Label nameLabel = new Label("Name: " + book.getTitle());
        Label authorLabel = new Label("Author: " + book.getAuthor());
        Label isbnLabel = new Label("ISBN: " + book.getISBN());
        Label availabilityLabel = new Label("Availability: " + (book.isAvailable() ? "Available" : "Not Available"));
        Label borrowerLabel = new Label("Borrower ID: " + (book.getBorrowerID() == null ? "N/A" : book.getBorrowerID()));
        Label borrowedDateLabel = new Label("Borrowed Date: " + (book.getBorrowedDate() == null ? "N/A" : book.getBorrowedDate()));

        layout.getChildren().addAll(nameLabel, authorLabel, isbnLabel, availabilityLabel, borrowerLabel, borrowedDateLabel);

        Scene scene = new Scene(layout, 300, 200);
        bookDetailsStage.setScene(scene);
        bookDetailsStage.show();
    }


    private void viewMembers() {
        Stage viewMembersStage = new Stage();
        viewMembersStage.setTitle("View Members");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        ListView<Member> listView = new ListView<>();
        listView.getItems().addAll(library.getMemberList()); // Add all members to the ListView
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Member member, boolean empty) {
                super.updateItem(member, empty);
                if (empty || member == null) {
                    setText(null);
                } else {
                    setText(member.getName() + " (Member ID: " + member.getMemberID() + ")");
                }
            }
        });

        // Double-click event for viewing detailed member info
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Member selectedMember = listView.getSelectionModel().getSelectedItem();
                if (selectedMember != null) {
                    showMemberDetails(selectedMember);
                }
            }
        });

        layout.getChildren().addAll(new Label("Library Members:"), listView);
        Scene scene = new Scene(layout, 400, 400);
        viewMembersStage.setScene(scene);
        viewMembersStage.show();
    }

    // Mini-window to show member details
    private void showMemberDetails(Member member) {
        Stage memberDetailsStage = new Stage();
        memberDetailsStage.setTitle("Member Details");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        Label nameLabel = new Label("Name: " + member.getName());
        Label memberIdLabel = new Label("Member ID: " + member.getMemberID());
        Label borrowedBooksLabel = new Label("Borrowed Books: " +
                (member.getBorrowedBooks().isEmpty() ? "None" : String.join(", ", member.getBorrowedBooks())));

        layout.getChildren().addAll(nameLabel, memberIdLabel, borrowedBooksLabel);

        Scene scene = new Scene(layout, 300, 200);
        memberDetailsStage.setScene(scene);
        memberDetailsStage.show();
    }


    private void searchBooks() {
        // Implement functionality to search books by title, author, or ISBN.
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void viewActiveLoans() {
        Stage activeLoansStage = new Stage();
        activeLoansStage.setTitle("Active Loans");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        ListView<Book> listView = new ListView<>();
        for (Book book : library.getBookList()) {
            if (!book.isAvailable()) {
                listView.getItems().add(book); // Add only borrowed books
            }
        }

        // Display books in a readable format
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getTitle() + " (ISBN: " + book.getISBN() + ")");
                }
            }
        });

        // Double-click event to show loan details
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Book selectedBook = listView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    showLoanDetails(selectedBook);
                }
            }
        });

        layout.getChildren().addAll(new Label("Active Loans:"), listView);
        Scene scene = new Scene(layout, 400, 400);
        activeLoansStage.setScene(scene);
        activeLoansStage.show();
    }

    private void showLoanDetails(Book book) {
        Stage loanDetailsStage = new Stage();
        loanDetailsStage.setTitle("Loan Details");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        // Find the borrower based on borrower ID
        Member borrower = null;
        for (Member member : library.getMemberList()) {
            if (member.getMemberID().equals(book.getBorrowerID())) {
                borrower = member;
                break;
            }
        }

        Label titleLabel = new Label("Title: " + book.getTitle());
        Label isbnLabel = new Label("ISBN: " + book.getISBN());
        Label borrowerIdLabel = new Label("Borrower ID: " + book.getBorrowerID());
        Label borrowerNameLabel = new Label("Borrower Name: " + (borrower != null ? borrower.getName() : "Unknown"));
        Label dueDateLabel = new Label("Due Date: " + book.getDueDate() + (book.isOverdue() ? " (Overdue)" : ""));

        layout.getChildren().addAll(titleLabel, isbnLabel, borrowerIdLabel, borrowerNameLabel, dueDateLabel);

        Scene scene = new Scene(layout, 300, 200);
        loanDetailsStage.setScene(scene);
        loanDetailsStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
