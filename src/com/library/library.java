package com.library;

import com.library.model.*;
import java.util.ArrayList;
import java.util.List;

public class library {
    private List<LibraryItem> items;
    private List<User> users;

    public library() {
        this.items = new ArrayList<>();
        this.users = new ArrayList<>();
        initializeLibrary();
    }

    private void initializeLibrary() {
        // Add initial books and magazines
        items.add(new Book("Book1", "Author1", "book1"));
        items.add(new Book("Book2", "Author2", "book2"));
        items.add(new Book("Book3", "Author3", "book3"));
        items.add(new Book("Book4", "Author4", "book4"));
        items.add(new Magazine("Magazine1", "AuthorM1", "Magazine1"));
        items.add(new Magazine("Magazine2", "AuthorM2", "Magazine2"));

        // Add initial users
        users.add(new User("Samith"));
        users.add(new User("Thameera"));
        users.add(new User("Jeehara"));
        users.add(new User("Vithum"));
        users.add(new User("Kaushalya"));
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public LibraryItem findItem(String serialNumber) {
        return items.stream()
                .filter(item -> item.getSerialNumber().equals(serialNumber))
                .findFirst()
                .orElse(null);
    }

    public User findUser(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<LibraryItem> getItems() {
        return items;
    }

    public List<User> getUsers() {
        return users;
    }
}