package library_project;

import java.time.LocalDateTime;
import java.util.*;

public class Library implements LibraryActivities {
    private int latestBookId;
    private int latestUserId;
    private final String libraryName;
    private int numberOfBooksInLibrary;
    private int numberOfLibraryUsers;
    private final Map<Integer, Book> listOfBooks = new HashMap<>();
    private final Map<Integer, User> listOfUsers = new HashMap<>();
    record BorrowEvent(int bookId, int userId, LocalDateTime when) {}
    private final List<BorrowEvent> lendHistory = new ArrayList<>();

    Library(String libraryName) {
        Objects.requireNonNull(libraryName, "libraryName");
        String n = libraryName.trim();
        if (n.isEmpty()) throw new IllegalArgumentException("Library name cannot be empty");
        this.libraryName = n;
    }

    public int getNumberOfBooksInLibrary() { return listOfBooks.size(); }
    public int getNumberOfLibraryUsers() { return listOfUsers.size(); }


    public Map<Integer, Book> getListOfBooks() {
        return java.util.Collections.unmodifiableMap(this.listOfBooks);
    }
    public Map<Integer, User> getListOfUsers() {
        return java.util.Collections.unmodifiableMap(this.listOfUsers);
    }

    @Override
    public Map<Integer, Book> addBookToLibrary(String bookName) {
        if (!bookName.trim().isEmpty()) {
            this.latestBookId += 1;
            Book newBook = new Book(bookName.trim().toUpperCase(), this.latestBookId);
            this.listOfBooks.put(this.latestBookId, newBook);
            this.numberOfBooksInLibrary = this.listOfBooks.size();
        } else {
            System.out.println("Wrong book name!");
        }
        return this.listOfBooks;
    }

    @Override
    public Map<Integer, User> registerNewLibraryUser(String userName) {
        if (!userName.trim().isEmpty()) {
            this.latestUserId += 1;
            User newUser = new User(userName.trim().toUpperCase(), this.latestUserId);
            newUser.setUserState(UserStates.REGISTERED);
            this.listOfUsers.put(this.latestUserId, newUser);
            this.numberOfLibraryUsers = this.listOfUsers.size();
        } else {
            System.out.println("Wrong user name!");
        }
        return this.listOfUsers;
    }

    @Override
    public List<BorrowEvent> borrowBook(int bookId, int userId) {
        if (!this.listOfUsers.containsKey(userId) || !this.listOfBooks.containsKey(bookId)) {
            System.out.println("There is either not a user or a book with entered ID");
            return lendHistory;
        }

        User bookBorrower = getUser(userId);
        Book book = getBook(bookId);

        if (book.getBookState() != BookStates.IN_LIBRARY) {
            System.out.println("Book is not available in library right now.");
            return lendHistory;
        }

        book.setBookBorrower(bookBorrower);
        book.setBookState(BookStates.OUT_OF_LIBRARY);
        addBookToUsersBorrowedBooksList(book, bookBorrower);

        lendHistory.add(new BorrowEvent(bookId, userId, LocalDateTime.now()));

        System.out.println("Great! User: " + bookBorrower.getUserName() + " ID: " + bookBorrower.getUserId());
        System.out.println("Book borrowed: " + book.getBookName() + " ID: " + book.getBookId());

        return lendHistory;
    }


    @Override
    public Book returnBookToLibrary(int bookId) {
        Book book = this.listOfBooks.get(bookId);
        if (book == null) {
            System.out.println("This book is not from our library");
            return null;
        }
        if (book.getBookState() == BookStates.IN_LIBRARY) {
            System.out.println("Book is already in library");
            return null;
        }
        User borrower = book.getBookBorrower();
        if (borrower != null) {
            borrower.getBorrowedBooks().remove(book);
        }
        book.setBookBorrower(null);
        book.setBookState(BookStates.IN_LIBRARY);
        return book;
    }


    public Book getBook(int bookId) {
        Book b = this.listOfBooks.get(bookId);
        if (b != null) {
            System.out.println("Book has been found and returned");
            return b;
        }
        System.out.println("Book with this ID has not been found");
        return null;
    }

    public User getUser(int userId) {
        User u = this.listOfUsers.get(userId);
        if (u != null) {
            System.out.println("User has been found and returned");
            return u;
        }
        System.out.println("User with this ID has not been found");
        return null;
    }

    public Set<Book> addBookToUsersBorrowedBooksList(Book book, User user) {
        user.getBorrowedBooks().add(book);
        return user.getBorrowedBooks();
    }

    @Override
    public void trackGeneralHistoryOfBorrowedBooks() {
        if (lendHistory.isEmpty()) {
            System.out.println("Borrow history is empty");
            return;
        }
        for (BorrowEvent ev : lendHistory) {
            Book b = listOfBooks.get(ev.bookId());
            User u = listOfUsers.get(ev.userId());
            System.out.println(ev.when() + " | user=" + (u != null ? u.getUserName() : ev.userId())
                    + " | book=" + (b != null ? b.getBookName() : ev.bookId()));
        }
    }


    @Override
    public void trackHistoryOfBorrowedBooksByUserID(int userId) {
        if (!listOfUsers.containsKey(userId)) {
            System.out.println("There is not user with ID: " + userId);
            return;
        }
        boolean any = false;
        for (BorrowEvent ev : lendHistory) {
            if (ev.userId() == userId) {
                Book b = listOfBooks.get(ev.bookId());
                System.out.println(ev.when() + " | " + (b != null ? b.getBookName() : ev.bookId()));
                any = true;
            }
        }
        if (!any) System.out.println("No borrow history for user " + userId);
    }


    @Override
    public User removeUserFromLibrary(int userId) {
        if (!this.listOfUsers.containsKey(userId)) {
            System.out.println("There was never such user");
            return null;
        } else if (getUser(userId).getUserState().equals(UserStates.NOT_REGISTERED)) {
            System.out.println("User already doesn't belong to library data base");
            return null;
        } else {
            getUser(userId).setUserState(UserStates.NOT_REGISTERED);
            return getUser(userId);
        }
    }

    @Override
    public Map<Integer, Book> removeBookFromLibrary(int bookId) {
        if (!this.listOfBooks.containsKey(bookId)) {
            System.out.println("There was never such book in library");
            return null;
        } else if (getBook(bookId).getBookState().equals(BookStates.OUT_OF_LIBRARY)) {
            System.out.println("This book is out of library, it has to be returned first");
            return null;
        } else {
            this.listOfBooks.remove(bookId);
            return this.listOfBooks;
        }
    }

    @Override
    public String toString() {
        return "Library{" + "libraryName='" + libraryName + '\'' + ", numberOfBooksInLibrary=" + numberOfBooksInLibrary + ", numberOfLibraryUsers=" + numberOfLibraryUsers + ", listOfBooks=" + listOfBooks + ", listOfUsers=" + listOfUsers + '}';
    }
}