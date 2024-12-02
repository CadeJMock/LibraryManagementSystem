/*
 * Cade Mock
 * CWID: 50350556
 * Date (Last Updated) : 12/1/2024
 * Email: cmock2@leomail.tamuc.edu
 */

package com.example.librarymanagementsystem;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application class for the Library Management System.
 * Extends the JavaFX Application class to provide a graphical user interface (GUI) for interacting with the library.
 *
 * Responsibilities of the LibraryApp class include:
 * - Managing the user interface and event handling for library operations.
 * - Providing access to core functionalities like adding books/members, borrowing/returning books, and viewing library records.
 * - Integrating with the Library class to handle backend data and operations.
 *
 * This class serves as the entry point for the application and provides a seamless interaction between the user and the library system.
 */
public class LibraryApp extends Application {
    private Library library = new Library();

    /**
     * The main entry point for the JavaFX application.
     * This method initializes the GUI, loads existing data, and sets up event handlers for user interactions.
     *
     * @param primaryStage The main stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        // Load library data from file (if available) on startup
        try {
            library.loadData();
        } catch (Exception e) {
            System.out.println("No existing data found. Starting fresh.");
        }

        // Main layout using a BorderPane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-padding: 20;");

        // Section 1: Add buttons - Add Book, Add Member
        VBox addButtons = new VBox(10); // Vertical box for "Add Features"
        addButtons.setStyle("-fx-padding: 10; -fx-border-color: #b0b0b0; -fx-border-width: 1; -fx-border-radius: 5;");
        Label addSectionLabel = new Label("Add Features");
        addSectionLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Button addBookButton = new Button("Add Book");
        addBookButton.setStyle("-fx-font-size: 12; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button addMemberButton = new Button("Add Member");
        addMemberButton.setStyle("-fx-font-size: 12; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButtons.getChildren().addAll(addSectionLabel, addBookButton, addMemberButton);

        // Section 2: Borrow/Return buttons - Borrow Book, Return Book,
        VBox borrowReturnButtons = new VBox(10); // Vertical box for "Borrow/Return"
        borrowReturnButtons.setStyle("-fx-padding: 10; -fx-border-color: #b0b0b0; -fx-border-width: 1; -fx-border-radius: 5;");
        Label borrowReturnSectionLabel = new Label("Borrow/Return");
        borrowReturnSectionLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setStyle("-fx-font-size: 12; -fx-background-color: #FF9800; -fx-text-fill: white;");
        Button returnBookButton = new Button("Return Book");
        returnBookButton.setStyle("-fx-font-size: 12; -fx-background-color: #FF9800; -fx-text-fill: white;");
        borrowReturnButtons.getChildren().addAll(borrowReturnSectionLabel, borrowBookButton, returnBookButton);

        // Section 3: View buttons - View Books, View Members, View Active Loans
        VBox viewButtons = new VBox(10); // Vertical box for "View Features"
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

        // Save and Exit button - For saving data and closing the application
        Button saveExitButton = new Button("Save and Exit");
        saveExitButton.setStyle("-fx-font-size: 12; -fx-background-color: #F44336; -fx-text-fill: white;");

        // Layout arrangement
        HBox topSection = new HBox(20, addButtons, borrowReturnButtons, viewButtons); // horizontal box for sections
        topSection.setStyle("-fx-alignment: center;"); // center-align the buttons
        mainLayout.setTop(topSection); // place the top section at the top of the layout
        mainLayout.setBottom(saveExitButton); // place and save the exit button at the button
        BorderPane.setAlignment(saveExitButton, Pos.BOTTOM_RIGHT); // Align the save and exit button to the bottom-right

        // Event handlers for the buttons (opens the windows for each)
        addBookButton.setOnAction(e -> addBook());
        addMemberButton.setOnAction(e -> addMember());
        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
        viewBooksButton.setOnAction(e -> viewBooks());
        viewMembersButton.setOnAction(e -> viewMembers());
        viewActiveLoansButton.setOnAction(e -> viewActiveLoans());
        saveExitButton.setOnAction(e -> { // Attempt to save the data to the file, catch an error if there was an error writing to it
            try {
                library.saveData();
                System.exit(0);
            } catch (Exception ex) {
                System.out.println("Failed to save data: " + ex.getMessage());
            }
        });

        // Set the scene and display the application window
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    /**
     * Displays a form for adding a new book to the library.
     * Validates the input fields to ensure all data is provided and the ISBN is unique.
     * Prevents non-numeric input in the ISBN field.
     */
    private void addBook() {
        Stage addBookStage = new Stage(); // create a new stage for adding a book
        addBookStage.setTitle("Add Book");

        VBox layout = new VBox(10); // vertical layout with spacing
        layout.setStyle("-fx-padding: 10;");

        TextField titleField = new TextField(); // input field for book title
        titleField.setPromptText("Enter Title");

        TextField authorField = new TextField(); // input field for book author
        authorField.setPromptText("Enter Author");

        TextField isbnField = new TextField(); // input field for book ISBN
        isbnField.setPromptText("Enter ISBN");

        isbnField.textProperty().addListener((observable, oldValue, newValue) -> { // This listener restricts the ISBN field to numeric input only
            if (!newValue.matches("\\d*")) { // if the new value contains non-numeric characters
                isbnField.setText(oldValue); // revert to the old value
            }
        });

        // Add button to submit the form
        Button addButton = new Button("Add Book");
        addButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button
        // event handler for the add button
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();


            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) { // validate that all fields are filled
                showAlert("Error", "All fields must be filled!");
            } else if (library.getBookList().stream().anyMatch(book -> book.getISBN().equals(isbn))) { // validate that the ISBN is unique
                showAlert("Error", "A book with this ISBN already exists!");
            } else { // Add the book to the library if validation passes
                library.addBook(new Book(title, author, isbn));
                showAlert("Success", "Book added successfully!");
                addBookStage.close();
            }
        });

        // add all components to the layout
        layout.getChildren().addAll(
                new Label("Add a New Book"), // header label
                titleField, authorField, isbnField, // input fields
                addButton // add button
        );

        // create and set the scene for the add book window
        Scene scene = new Scene(layout, 300, 200);
        addBookStage.setScene(scene);
        addBookStage.show();
    }

    /**
     * Displays a form for adding a new member to the library.
     * Validates the input fields to ensure all data is provided and the Member ID is unique.
     */
    private void addMember() {
        Stage addMemberStage = new Stage(); // Create a new stage (window) for adding a member
        addMemberStage.setTitle("Add Member");

        VBox layout = new VBox(10); // Create a vertical layout with spacing
        layout.setStyle("-fx-padding: 10;");

        TextField nameField = new TextField(); // Input field for the member's name
        nameField.setPromptText("Enter Name");

        TextField memberIdField = new TextField(); // Input field for the member's ID
        memberIdField.setPromptText("Enter Member ID");

        Button addButton = new Button("Add Member"); // Add button to submit the form
        addButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String memberId = memberIdField.getText();

            if (name.isEmpty() || memberId.isEmpty()) { // Validate that all fields are filled
                showAlert("Error", "All fields must be filled!");
            } else if (library.getMemberList().stream().anyMatch(member -> member.getMemberID().equals(memberId))) { // Validate that the Member ID is unique
                showAlert("Error", "A member with this ID already exists!");
            } else { // Add the member to the library if validation passes
                library.addMember(new Member(name, memberId));
                showAlert("Success", "Member added successfully!");
                addMemberStage.close();
            }
        });

        // Add all components to the layout
        layout.getChildren().addAll(
                new Label("Add a New Member"),
                nameField, memberIdField,
                addButton
        );

        // Create and set the scene for the Add Member window
        Scene scene = new Scene(layout, 300, 150);
        addMemberStage.setScene(scene);
        addMemberStage.show();
    }

    /**
     * Displays a form for borrowing a book by entering its ISBN and the member's ID.
     * Provides search and selection features for both books and members.
     * Ensures only available books can be borrowed and validates all input fields.
     */
    private void borrowBook() {
        Stage borrowBookStage = new Stage(); // Create a new stage for borrowing a book
        borrowBookStage.setTitle("Borrow Book");

        // Main layout for the stage
        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-padding: 15;");

        // Section 1: Book search and selection
        Label bookSearchLabel = new Label("Search for a Book");
        TextField bookSearchField = new TextField();
        bookSearchField.setPromptText("Search by Title, Author, or ISBN");

        // List to display the available books
        ListView<Book> bookListView = new ListView<>();

        // Input field for ISBN
        TextField isbnField = new TextField();
        isbnField.setPromptText("Enter Book ISBN");

        // Restrict ISBN field to numbers only with a listener
        isbnField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // allow digits only
                isbnField.setText(oldValue); // revert to old value if invalid input
            }
        });

        // Populate the book list with available books
        updateBookList(bookListView, library.getBookList().stream()
                .filter(Book::isAvailable)
                .toList());

        // Dynamically update the book list based on search input using a listener
        bookSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBookList(bookListView, library.getBookList().stream()
                    .filter(book -> book.isAvailable() && // if book is available
                            (book.getTitle().toLowerCase().contains(newValue.toLowerCase()) || // if the title contains the input
                                    book.getAuthor().toLowerCase().contains(newValue.toLowerCase()) || // if the author contains the input
                                    book.getISBN().toLowerCase().contains(newValue.toLowerCase()))) // if the ISBN contains the input
                    .toList()); // add it to the list
        });

        // Populate ISBN field when book is selected from the list
        bookListView.setOnMouseClicked(event -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                isbnField.setText(selectedBook.getISBN());
            }
        });

        // label to highlight that the ISBN is a required field
        Label isbnLabel = new Label("Book ISBN *");
        isbnLabel.setStyle("-fx-text-fill: red;"); // Highlight required field

        // Section 2: Member search and selection
        Label memberSearchLabel = new Label("Search for a Member");
        TextField memberSearchField = new TextField();
        memberSearchField.setPromptText("Search by Name or Member ID");

        // List to display all members
        ListView<Member> memberListView = new ListView<>();

        // Input field for Member ID
        TextField memberIdField = new TextField();
        memberIdField.setPromptText("Enter Member ID");

        // Populate member list with all members
        updateMemberList(memberListView, library.getMemberList());

        // Dynamically update the member list based on search input using a listener
        memberSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateMemberList(memberListView, library.getMemberList().stream()
                    .filter(member -> member.getName().toLowerCase().contains(newValue.toLowerCase()) || // if the member name contains the input
                            member.getMemberID().toLowerCase().contains(newValue.toLowerCase())) // if the member id contains the input
                    .toList()); // add them to the list
        });

        // Populate Member ID field when member is selected from the list
        memberListView.setOnMouseClicked(event -> {
            Member selectedMember = memberListView.getSelectionModel().getSelectedItem();
            if (selectedMember != null) {
                memberIdField.setText(selectedMember.getMemberID());
            }
        });

        // Label to highlight that Member ID is a required field
        Label memberIdLabel = new Label("Member ID *");
        memberIdLabel.setStyle("-fx-text-fill: red;"); // Highlight required field

        // Borrow button to submit the form
        Button borrowButton = new Button("Borrow Book");
        borrowButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        borrowButton.setOnAction(e -> {
            String isbn = isbnField.getText(); // get the entered ISBN
            String memberId = memberIdField.getText(); // get the entered member ID

            // validate that both fields are filled
            if (isbn.isEmpty() || memberId.isEmpty()) {
                showAlert("Error", "Both fields must be filled!");
                return;
            }

            // attempt to borrow the book
            boolean success = library.borrowBook(isbn, memberId);
            if (success) {
                showAlert("Success", "Book borrowed successfully!");
                borrowBookStage.close(); // close the borrow book window
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

        // set the scene and display the borrow book window
        Scene scene = new Scene(mainLayout, 500, 700);
        borrowBookStage.setScene(scene);
        borrowBookStage.show();
    }

    /**
     * Displays a form for returning a book by entering its ISBN.
     * Provides a search and selection feature to find checked-out books.
     * Ensures the entered ISBN corresponds to a currently checked-out book.
     */
    private void returnBook() {
        Stage returnBookStage = new Stage(); // new stage for returning a book
        returnBookStage.setTitle("Return Book");

        // main layout for stage
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");

        // Section 1: book search and selection
        Label bookSearchLabel = new Label("Search for a Book");
        TextField bookSearchField = new TextField();
        bookSearchField.setPromptText("Search by Title, Author, or ISBN");

        // list to display checked-out books
        ListView<Book> bookListView = new ListView<>();

        // input field for ISBN
        TextField isbnField = new TextField();
        isbnField.setPromptText("Enter Book ISBN");

        // populate the book list with currently checked-out books
        updateBookList(bookListView, library.getBookList().stream()
                .filter(book -> !book.isAvailable())
                .toList());

        bookSearchField.textProperty().addListener((observable, oldValue, newValue) -> { // Dynamically update the book list based on search input
            updateBookList(bookListView, library.getBookList().stream()
                    .filter(book -> !book.isAvailable() &&
                            (book.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                                    book.getAuthor().toLowerCase().contains(newValue.toLowerCase()) ||
                                    book.getISBN().toLowerCase().contains(newValue.toLowerCase())))
                    .toList());
        });

        bookListView.setOnMouseClicked(event -> { // Populate the ISBN field when a book is selected from the list
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                isbnField.setText(selectedBook.getISBN());
            }
        });

        // Label to highlight that ISBN is a required field
        Label isbnLabel = new Label("Book ISBN *");
        isbnLabel.setStyle("-fx-text-fill: red;"); // Highlight required field

        // Return button to submit the form
        Button returnButton = new Button("Return Book");
        returnButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green button

        // Event handler for the Return button
        returnButton.setOnAction(e -> {
            String isbn = isbnField.getText();

            if (isbn.isEmpty()) { // Validate that the ISBN field is filled
                showAlert("Error", "ISBN field must be filled!");
                return;
            }

            // Attempt to return the book
            boolean success = library.returnBook(isbn, null);
            if (success) {
                showAlert("Success", "Book returned successfully!");
                returnBookStage.close();
            } else {
                showAlert("Error", "Return failed! Make sure the book exists and is checked out.");
            }
        });

        // add all components to the layout
        layout.getChildren().addAll(
                bookSearchLabel, bookSearchField, bookListView, isbnLabel, isbnField,
                returnButton
        );

        // new scene for the return book window
        Scene scene = new Scene(layout, 400, 500);
        returnBookStage.setScene(scene);
        returnBookStage.show();
    }

    /**
     * Updates the book list in the specified ListView when books are added or removed.
     * Attaches context menu options (Open, Delete) and double-click functionality to each book.
     *
     * @param listView The ListView to update with the list of books.
     * @param books    The list of books to display in the ListView.
     */
    private void updateBookList(ListView<Book> listView, List<Book> books) {
        listView.getItems().clear(); // clear the current items in the ListView
        listView.getItems().addAll(books); // add the updated list of books to the ListView

        listView.setCellFactory(param -> { // Set a custom cell factory to define how each book is displayed/interacted with
            ListCell<Book> cell = new ListCell<>() { // define how each book is displayed
                @Override
                protected void updateItem(Book book, boolean empty) {
                    super.updateItem(book, empty);
                    if (empty || book == null) {
                        setText(null); // no text if the cell is empty
                    } else { // format the book details for display
                        setText(book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getISBN() + ")");
                    }
                }
            };

            // context menu for each book
            ContextMenu contextMenu = new ContextMenu();

            // Open option to view book details
            MenuItem openItem = new MenuItem("Open");
            openItem.setOnAction(e -> {
                Book selectedBook = cell.getItem(); // get the selected book
                if (selectedBook != null) {
                    showBookDetails(selectedBook); // show details of the selected book
                }
            });

            // Delete option to remove the book
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                Book selectedBook = cell.getItem(); // get the selected book
                if (selectedBook != null) {
                    // confirmation dialog before deleting the book
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Delete Book");
                    confirmationDialog.setHeaderText("Are you sure?");
                    confirmationDialog.setContentText("Deleting this book will return it if checked out and remove it from all active loans.");

                    // options for the confirmation dialog
                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

                    // handle user response to the confirmation dialog
                    confirmationDialog.showAndWait().ifPresent(response -> {
                        if (response == yesButton) {
                            // Return the book if it is checked out
                            if (!selectedBook.isAvailable()) {
                                library.returnBook(selectedBook.getISBN(), selectedBook.getBorrowerID());
                            }

                            // Remove the book from the library and update the list
                            library.getBookList().remove(selectedBook);
                            updateBookList(listView, library.getBookList());
                        }
                    });
                }
            });

            // add the open and delete options to the context menu
            contextMenu.getItems().addAll(openItem, deleteItem);
            // attach the context menu to the cell
            cell.setContextMenu(contextMenu);

            // add double-click functionality to open book details
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !cell.isEmpty()) {
                    showBookDetails(cell.getItem()); // show details of the double-clicked book
                }
            });

            return cell; // return the customized cell
        });
    }

    /**
     * Updates the member list in the specified ListView when members are added or removed.
     * Attaches context menu options (Open, Delete) and double-click functionality to each member.
     *
     * @param listView The ListView to update with the list of members.
     * @param members  The list of members to display in the ListView.
     */
    private void updateMemberList(ListView<Member> listView, List<Member> members) { // similar to updateBookList, see comments for that if needed
        listView.getItems().clear();
        listView.getItems().addAll(members);

        listView.setCellFactory(param -> {
            ListCell<Member> cell = new ListCell<>() {
                @Override
                protected void updateItem(Member member, boolean empty) {
                    super.updateItem(member, empty);
                    if (empty || member == null) {
                        setText(null);
                    } else {
                        setText(member.getName() + " (ID: " + member.getMemberID() + ")"); // format text
                    }
                }
            };

            ContextMenu contextMenu = new ContextMenu();

            // Open option
            MenuItem openItem = new MenuItem("Open");
            openItem.setOnAction(e -> {
                Member selectedMember = cell.getItem();
                if (selectedMember != null) {
                    showMemberDetails(selectedMember);
                }
            });

            // Delete option
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                Member selectedMember = cell.getItem();
                if (selectedMember != null) {
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Delete Member");
                    confirmationDialog.setHeaderText("Are you sure?");
                    confirmationDialog.setContentText("Deleting this member will return all their books and remove their active loans.");

                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

                    confirmationDialog.showAndWait().ifPresent(response -> {
                        if (response == yesButton) {
                            // Return all books borrowed by the member
                            List<String> borrowedBooksCopy = new ArrayList<>(selectedMember.getBorrowedBooks());
                            for (String isbn : borrowedBooksCopy) {
                                library.returnBook(isbn, selectedMember.getMemberID());
                            }

                            // Remove the member from the library and update the list
                            library.getMemberList().remove(selectedMember);
                            updateMemberList(listView, library.getMemberList());
                        }
                    });
                }
            });

            // add the options to the context menu
            contextMenu.getItems().addAll(openItem, deleteItem);
            cell.setContextMenu(contextMenu);

            // Double-click to open member details
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !cell.isEmpty()) {
                    showMemberDetails(cell.getItem());
                }
            });

            return cell; // reutnr the customized cell
        });
    }

    /**
     * Displays a window with all the books in the library.
     * Provides options to search, view details, and delete books.
     */
    private void viewBooks() {
        Stage viewBooksStage = new Stage(); // new stage for viewing books
        viewBooksStage.setTitle("View Books");

        VBox layout = new VBox(10); // vertical layout for the stage
        layout.setStyle("-fx-padding: 15;");

        TextField searchField = new TextField(); // search field to filter books by title/author/isbn
        searchField.setPromptText("Search by Title, Author, or ISBN");

        ListView<Book> bookListView = new ListView<>(); // listview to display the books
        updateBookList(bookListView, library.getBookList()); // populate with all books

        // Reapply CellFactory to ensure event handlers are retained (context menu/double-click)
        bookListView.setCellFactory(param -> {
            // define how each book is displayed
            ListCell<Book> cell = new ListCell<>() {
                @Override
                protected void updateItem(Book book, boolean empty) {
                    super.updateItem(book, empty);
                    if (empty || book == null) {
                        setText(null); // no text if cell is empty
                    } else {
                        setText(book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getISBN() + ")"); // text formatting
                    }
                }
            };

            // context menu with options for each book
            ContextMenu contextMenu = new ContextMenu();

            // Open option to view book details
            MenuItem openItem = new MenuItem("Open");
            openItem.setOnAction(e -> {
                Book selectedBook = cell.getItem();
                if (selectedBook != null) {
                    showBookDetails(selectedBook);
                }
            });

            // Delete option to remove the book
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                Book selectedBook = cell.getItem(); // get selected book
                if (selectedBook != null) {
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION); // confirmation dialogue
                    confirmationDialog.setTitle("Delete Book");
                    confirmationDialog.setHeaderText("Are you sure?");
                    confirmationDialog.setContentText("Deleting this book will return it if checked out and remove it from all active loans.");

                    // confirmation dialogue options
                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

                    // handle user response to configuration dialogue
                    confirmationDialog.showAndWait().ifPresent(response -> {
                        if (response == yesButton) {
                            // Return the book if it is checked out
                            if (!selectedBook.isAvailable()) {
                                library.returnBook(selectedBook.getISBN(), selectedBook.getBorrowerID());
                            }

                            // Remove the book from the library and update the list
                            library.getBookList().remove(selectedBook);
                            updateBookList(bookListView, library.getBookList());
                        }
                    });
                }
            });

            // add the open and delete options to the context menu
            contextMenu.getItems().addAll(openItem, deleteItem);
            // attach the context menu to the cell
            cell.setContextMenu(contextMenu);

            // add double-click functionality to open book details
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !cell.isEmpty()) {
                    showBookDetails(cell.getItem()); // show the details of the double-clicked book
                }
            });

            return cell; // return the customized cell
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> { // search field with listener to check for values that contain our input
            updateBookList(bookListView, library.getBookList().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getAuthor().toLowerCase().contains(newValue.toLowerCase()) ||
                            book.getISBN().toLowerCase().contains(newValue.toLowerCase()))
                    .toList());
        });

        // add all components to the layout
        layout.getChildren().addAll(new Label("Search Books"), searchField, bookListView);

        // create and set the scene for the View Books window
        Scene scene = new Scene(layout, 400, 600);
        viewBooksStage.setScene(scene);
        viewBooksStage.show();
    }

    /**
     * Displays a mini-window with detailed information about a specific book.
     *
     * @param book The book whose details are to be displayed.
     */
    private void showBookDetails(Book book) {
        Stage bookDetailsStage = new Stage();
        bookDetailsStage.setTitle("Book Details");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        // labels for displaying the book's information
        Label nameLabel = new Label("Title: " + book.getTitle());
        Label authorLabel = new Label("Author: " + book.getAuthor());
        Label isbnLabel = new Label("ISBN: " + book.getISBN());
        Label availabilityLabel = new Label("Availability: " + (book.isAvailable() ? "Available" : "Not Available"));
        Label borrowerLabel = new Label("Borrower ID: " + (book.getBorrowerID() == null ? "N/A" : book.getBorrowerID()));
        Label borrowedDateLabel = new Label("Borrowed Date: " + (book.getBorrowedDate() == null ? "N/A" : book.getBorrowedDate()));

        // add all the labels to the layout
        layout.getChildren().addAll(nameLabel, authorLabel, isbnLabel, availabilityLabel, borrowerLabel, borrowedDateLabel);

        // create and set the scene for the book details window
        Scene scene = new Scene(layout, 300, 200);
        bookDetailsStage.setScene(scene);
        bookDetailsStage.show(); // display
    }

    /**
     * Displays a window with all the members in the library.
     * Provides options to search, view details, and delete members.
     */
    private void viewMembers() {
        Stage viewMembersStage = new Stage();
        viewMembersStage.setTitle("View Members");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");

        // search field to filter members by name/member id
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Name or Member ID");

        // listview to display the membrs
        ListView<Member> memberListView = new ListView<>();
        updateMemberList(memberListView, library.getMemberList());

        // custom cellfactory to handle how each member is displayed and interacted with
        memberListView.setCellFactory(param -> {
            // define how each member is displayed
            ListCell<Member> cell = new ListCell<>() {
                @Override
                protected void updateItem(Member member, boolean empty) {
                    super.updateItem(member, empty);
                    if (empty || member == null) {
                        setText(null); // no text if cell is empty
                    } else {
                        setText(member.getName() + " (ID: " + member.getMemberID() + ")"); // text formatting
                    }
                }
            };

            ContextMenu contextMenu = new ContextMenu();

            // Open option
            MenuItem openItem = new MenuItem("Open");
            openItem.setOnAction(e -> {
                Member selectedMember = cell.getItem();
                if (selectedMember != null) {
                    showMemberDetails(selectedMember);
                }
            });

            // Delete option
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                Member selectedMember = cell.getItem();
                if (selectedMember != null) {
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Delete Member");
                    confirmationDialog.setHeaderText("Are you sure?");
                    confirmationDialog.setContentText("Deleting this member will return all their books and remove their active loans.");

                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

                    confirmationDialog.showAndWait().ifPresent(response -> {
                        if (response == yesButton) {
                            // Safely iterate over borrowed books
                            List<String> borrowedBooksCopy = new ArrayList<>(selectedMember.getBorrowedBooks());
                            for (String isbn : borrowedBooksCopy) {
                                library.returnBook(isbn, selectedMember.getMemberID());
                            }

                            // Remove the member from the library
                            library.getMemberList().remove(selectedMember);
                            updateMemberList(memberListView, library.getMemberList());
                        }
                    });
                }
            });

            contextMenu.getItems().addAll(openItem, deleteItem);
            cell.setContextMenu(contextMenu);

            // Double-click to open member details
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !cell.isEmpty()) {
                    showMemberDetails(cell.getItem());
                }
            });

            return cell; // return the customized cell
            // see previous viewX() functions for more comments, similar code
        });

        // listener for the search field, dynamically filter the member list
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateMemberList(memberListView, library.getMemberList().stream()
                    .filter(member -> member.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                            member.getMemberID().toLowerCase().contains(newValue.toLowerCase()))
                    .toList());
        });

        layout.getChildren().addAll(new Label("Search Members"), searchField, memberListView);

        Scene scene = new Scene(layout, 400, 600);
        viewMembersStage.setScene(scene);
        viewMembersStage.show();
    }

    /**
     * Displays a mini-window with detailed information about a specific library member.
     *
     * @param member The member whose details are to be displayed.
     */
    private void showMemberDetails(Member member) {
        Stage memberDetailsStage = new Stage();
        memberDetailsStage.setTitle("Member Details");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10;");

        // labels for member information
        Label nameLabel = new Label("Name: " + member.getName());
        Label memberIdLabel = new Label("Member ID: " + member.getMemberID());
        // display the list of books borrowed by the member
        Label borrowedBooksLabel = new Label("Borrowed Books: " +
                (member.getBorrowedBooks().isEmpty() ? "None" : String.join(", ", member.getBorrowedBooks())));

        layout.getChildren().addAll(nameLabel, memberIdLabel, borrowedBooksLabel);

        Scene scene = new Scene(layout, 300, 200);
        memberDetailsStage.setScene(scene);
        memberDetailsStage.show();
    }

    /**
     * Helper function to display an alert dialog with a given title and message.
     * This method is reusable for various informational prompts to the user.
     *
     * @param title   The title of the alert dialog.
     * @param message The content message to be displayed in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // create a new alert of type INFORMATION
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait(); // display the alert dialog and wait for the user's response/acknowledgment
    }

    /**
     * Displays a window with all active loans in the library.
     * Provides options to search, view details, and delete loans. Overdue loans are highlighted in red.
     */
    private void viewActiveLoans() {
        Stage viewActiveLoansStage = new Stage();
        viewActiveLoansStage.setTitle("View Active Loans");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 15;");

        // search field for filtering active loans by title/borrower id/borrowed date
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Title, Borrower ID, or Borrowed Date");

        ListView<Book> loanListView = new ListView<>();
        // populate the ListView with checked-out books
        updateBookListForLoans(loanListView, library.getBookList().stream()
                .filter(book -> !book.isAvailable()) // Only show checked-out books
                .toList());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> { // another listener to dynamically filter the active loans
            updateBookListForLoans(loanListView, library.getBookList().stream()
                    .filter(book -> !book.isAvailable() && // only include checked-out books
                            (book.getTitle().toLowerCase().contains(newValue.toLowerCase()) ||
                                    (book.getBorrowerID() != null && book.getBorrowerID().toLowerCase().contains(newValue.toLowerCase())) ||
                                    (book.getBorrowedDate() != null && book.getBorrowedDate().toString().contains(newValue)))
                    )
                    .toList());
        });

        // add the search field and listview to the layout
        layout.getChildren().addAll(new Label("Search Active Loans"), searchField, loanListView);

        Scene scene = new Scene(layout, 400, 600);
        viewActiveLoansStage.setScene(scene);
        viewActiveLoansStage.show(); // display
    }

    /**
     * Helper function to update the book list in the context of active loans.
     * This ensures loans are displayed correctly, highlights overdue loans in red,
     * and attaches context menu options (Open, Delete) and double-click functionality.
     *
     * @param listView The ListView to update with the active loans.
     * @param books    The list of books to display (only those currently loaned out).
     */
    private void updateBookListForLoans(ListView<Book> listView, List<Book> books) {
        listView.getItems().clear();
        listView.getItems().addAll(books);

        listView.setCellFactory(param -> {
            ListCell<Book> cell = new ListCell<>() {
                @Override
                protected void updateItem(Book book, boolean empty) {
                    super.updateItem(book, empty);
                    if (empty || book == null) {
                        setText(null); // no text if cell is empty
                        setStyle(""); // reset style
                    } else {
                        // format the loan details for display
                        String text = book.getTitle() + " (Borrower ID: " + book.getBorrowerID() + ")";
                        setText(text);

                        // Highlight overdue books in red
                        if (book.getBorrowedDate() != null && book.getBorrowedDate().plusWeeks(1).isBefore(LocalDate.now())) {
                            setStyle("-fx-text-fill: red;"); // set the text color to red
                        } else {
                            setStyle(""); // reset style for non-overdue books
                        }
                    }
                }
            };

            ContextMenu contextMenu = new ContextMenu();

            // Open option
            MenuItem openItem = new MenuItem("Open");
            openItem.setOnAction(e -> {
                Book selectedBook = cell.getItem(); // get selected loan
                if (selectedBook != null) {
                    showLoanDetails(selectedBook); // show details of the loan
                }
            });

            // Delete option
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                Book selectedLoan = cell.getItem();
                if (selectedLoan != null) {
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Delete Loan");
                    confirmationDialog.setHeaderText("Are you sure?");
                    confirmationDialog.setContentText("Deleting this loan will make the book available and update the borrower's record.");

                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

                    confirmationDialog.showAndWait().ifPresent(response -> {
                        if (response == yesButton) {
                            // Mark book as available and update member
                            library.returnBook(selectedLoan.getISBN(), selectedLoan.getBorrowerID());
                            updateBookListForLoans(listView, library.getBookList().stream()
                                    .filter(book -> !book.isAvailable())
                                    .toList());
                        }
                    });
                }
            });

            contextMenu.getItems().addAll(openItem, deleteItem);
            cell.setContextMenu(contextMenu);

            // Double-click to open loan details
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !cell.isEmpty()) {
                    showLoanDetails(cell.getItem());
                }
            });

            return cell; // return the customized cell
        });
    }

    /**
     * Displays a mini-window with detailed information about a specific loan.
     *
     * @param book The book representing the loan whose details are to be displayed.
     */
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

        // labels for displaying the loan's details
        Label titleLabel = new Label("Title: " + book.getTitle());
        Label isbnLabel = new Label("ISBN: " + book.getISBN());
        Label borrowerIdLabel = new Label("Borrower ID: " + book.getBorrowerID());
        Label borrowerNameLabel = new Label("Borrower Name: " + (borrower != null ? borrower.getName() : "Unknown")); // borrowers name if found
        Label dueDateLabel = new Label("Due Date: " + book.getDueDate() + (book.isOverdue() ? " (Overdue)" : "")); // due date and overdue status

        layout.getChildren().addAll(titleLabel, isbnLabel, borrowerIdLabel, borrowerNameLabel, dueDateLabel);

        Scene scene = new Scene(layout, 300, 200);
        loanDetailsStage.setScene(scene);
        loanDetailsStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
