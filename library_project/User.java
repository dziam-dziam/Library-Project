package library_project;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private final String userName;
    private final Integer userId;
    private UserStates userState = UserStates.NOT_REGISTERED;
    private final Set<Book> borrowedBooks = new LinkedHashSet<>();

    public User(String userName, Integer userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }


    public Integer getUserId() {
        return userId;
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setUserState(UserStates userState) {
        this.userState = userState;
    }

    public UserStates getUserState() {
        return userState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                ", userState=" + userState +
                '}';
    }
}
