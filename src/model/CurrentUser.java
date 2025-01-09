package model;

public class CurrentUser {
    private static CurrentUser instance;
    private Customer user;

    private CurrentUser() {}

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public void clearUser() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }
}