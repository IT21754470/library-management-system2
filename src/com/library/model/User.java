package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<LibraryItem> borrowedItems;

    public User(String name) {
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public String getName() { return name; }

    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
        item.setBorrowed(true);
    }

    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
        item.setBorrowed(false);
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }
}