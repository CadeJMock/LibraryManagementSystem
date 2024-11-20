package com.example.librarymanagementsystem;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-padding: 20;");

        // Section 1: Add buttons
        VBox addButtons = new VBox(10);
        addButtons.setStyle("-fx-padding: 10; -fx-border-color: #b0b0b0; -fx-border-width: 1; -fx-border-radius: 5;");
        Label addSectionLabel = new Label("Add Features");
        addSectionLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Button addBookButton = new Button("Add Book");
        addBookButton.setStyle("-fx-font-size: 12; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button addMemberButton = new Button("Add Member");
        addMemberButton.setStyle("-fx-font-size: 12; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButtons.getChildren().addAll(addSectionLabel, addBookButton, addMemberButton);

        // Section 2: Borrow/Return buttons
        VBox borrowReturnButtons = new VBox(10);
        borrowReturnButtons.setStyle("-fx-padding: 10; -fx-border-color: #b0b0b0; -fx-border-width: 1; -fx-border-radius: 5;");
        Label borrowReturnSectionLabel = new Label("Borrow/Return");
        borrowReturnSectionLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setStyle("-fx-font-size: 12; -fx-background-color: #FF9800; -fx-text-fill: white;");
        Button returnBookButton = new Button("Return Book");
        returnBookButton.setStyle("-fx-font-size: 12; -fx-background-color: #FF9800; -fx-text-fill: white;");
        borrowReturnButtons.getChildren().addAll(borrowReturnSectionLabel, borrowBookButton, returnBookButton);

        // Section 3: View buttons
        VBox viewButtons = new VBox(10);
        viewButtons.setStyle("-fx-padding: 10; -fx-border-color: #b0b0b0; -fx-border-width: 1; -fx-border-radius: 5;");
        Label viewSectionLabel = new Label("View Features");
        viewSectionLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Button viewBooksButton = new Button("View Books");
        viewBooksButton.setStyle("-fx-font-size: 12; -fx-background-color: #2196F3; -fx-text-fill: white;");
        Button viewMembersButton = new Button("View Members");
        viewMembersButton.setStyle("-fx-font-size: 12; -fx-background-color: #2196F3; -fx-text-fill: white;");
        Button viewActiveLoansButton = new Button("View Active Loans");
        viewActiveLoansButton.setStyle("-fx-font-size: 12; -fx-background-color: #2196F3; -fx-text-fill: white;");
        viewButtons.getChildren().addAll(viewSectionLabel, viewBooksButton, viewMembersButton, viewActiveLoansButton);

        // Save and Exit button
        Button saveExitButton = new Button("Save and Exit");
        saveExitButton.setStyle("-fx-font-size: 12; -fx-background-color: #F44336; -fx-text-fill: white;");

        // Layout arrangement
        HBox topSection = new HBox(20, addButtons, borrowReturnButtons, viewButtons);
        topSection.setStyle("-fx-alignment: center;");
        mainLayout.setTop(topSection);
        mainLayout.setBottom(saveExitButton);
        BorderPane.setAlignment(saveExitButton, Pos.BOTTOM_RIGHT);

        // Set button actions
        addBookButton.setOnAction(e -> addBook());
        addMemberButton.setOnAction(e -> addMember());
        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
        viewBooksButton.setOnAction(e -> viewBooks());
        viewMembersButton.setOnAction(e -> viewMembers());
        viewActiveLoansButton.setOnAction(e -> viewActiveLoans());
        saveExitButton.setOnAction(e -> {
            try {
                library.saveData();
                System.exit(0);
            } catch (Exception ex) {
                System.out.println("Failed to save data: " + ex.getMessage());
            }
        });

        // Set scene
        Scene scene = new Scene(mainLayout, 600, 400);
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
                    setText(book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getISBN() + ")");
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

        // Main layout
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");

        // Search bar
        Label searchLabel = new Label("Search Books");
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Title, Author, or ISBN");

        // Book list
        ListView<Book> bookListView = new ListView<>();
        updateBookList(bookListView, library.getBookList());

        // Dynamically filter book list based on search query
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBookList(bookListView, library.getBookList().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getISBN().toLowerCase().contains(newValue.toLowerCase()))
                    .toList());
        });

        // Double-click event to show book details
        bookListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    showBookDetails(selectedBook);
                }
            }
        });

        // Add components to layout
        layout.getChildren().addAll(searchLabel, searchField, bookListView);

        Scene scene = new Scene(layout, 400, 600);
        viewBooksStage.setScene(scene);
        viewBooksStage.show();
    }


    // Mini-window to show book details
    private void showBookDetails(Book book) {
        Stage bookDetailsStage = new Stage();
        bookDetailsStage.setTitle("Book Details");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        Label nameLabel = new Label("Title: " + book.getTitle());
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
