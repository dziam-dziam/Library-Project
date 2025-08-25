package library_project;

public class Main {
    public static void main(String[] args) {
        //ADDING BOOKS TO LIBRARY AND CHECKING THE OUTCOMES
        Library library = new Library("Biblioteka Ratajskich");
        library.addBookToLibrary("Lord of The Rings");
        library.addBookToLibrary("Metro 2033");
        library.addBookToLibrary("Game of Thrones");
        library.addBookToLibrary("");
        library.addBookToLibrary(" ");
        library.addBookToLibrary("Game of Thrones");
        library.getListOfBooks();
        library.getNumberOfBooksInLibrary();

        //ADDING LIBRARY USERS AND CHECKING THE OUTCOMES
        library.registerNewLibraryUser("Janek");
        library.registerNewLibraryUser("Ola");
        library.registerNewLibraryUser("");
        library.registerNewLibraryUser("   ");
        library.registerNewLibraryUser("Frano");
        library.registerNewLibraryUser("Marek");
        library.getListOfUsers();
        library.getNumberOfLibraryUsers();

        //BORROWING BOOKS AND CHECKING THE OUTCOMES
        library.borrowBook(1, 3);
        library.borrowBook(2, 3);
        library.borrowBook(3, 3);
        library.borrowBook(2, 4);
        library.getBook(1);

        //TRACKING THE BORROW HISTORY AND CHECKING THE OUTCOMES
        library.trackGeneralHistoryOfBorrowedBooks();
        System.out.println(library.getUser(3).getBorrowedBooks());
        System.out.println(library.getUser(4).getBorrowedBooks());
        library.trackHistoryOfBorrowedBooksByUserID(3);


    }
}
