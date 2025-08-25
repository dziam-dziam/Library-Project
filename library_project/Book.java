package library_project;

import java.util.Objects;

public class Book {
    private final String bookName;
    private final Integer bookId;
    private BookStates bookState = BookStates.IN_LIBRARY;
    private User bookBorrower;

    public Book(String name, int bookId) {
        this.bookName = name.trim();
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public BookStates getBookState() {
        return bookState;
    }

    public void setBookState(BookStates bookState) {
        this.bookState = bookState;
    }

    public void setBookBorrower(User bookBorrower) {
        this.bookBorrower = bookBorrower;
    }

    public User getBookBorrower() {
        return bookBorrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId) && Objects.equals(bookName, book.bookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", bookId=" + bookId +
                ", bookState=" + bookState +
                ", bookBorrower=" + bookBorrower +
                '}';
    }
}
