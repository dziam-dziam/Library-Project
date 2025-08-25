package library_project;

import java.util.List;
import java.util.Map;

public interface LibraryActivities {
    Map<Integer, Book> addBookToLibrary(String bookName);

    Map<Integer, User> registerNewLibraryUser(String userName);

    List<Library.BorrowEvent> borrowBook(int bookId, int userId);

    Book returnBookToLibrary(int bookId);

    void trackGeneralHistoryOfBorrowedBooks();

    void trackHistoryOfBorrowedBooksByUserID(int userId);

    User removeUserFromLibrary(int userId);

    Map<Integer, Book> removeBookFromLibrary(int bookId);
}
