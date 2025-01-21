package com.library.model;

public abstract class LibraryItem {
    private String title;
    private String author;
    private String serialNumber;
    private boolean isBorrowed;

    public LibraryItem(String title, String author, String serialNumber) {
        this.title = title;
        this.author = author;
        this.serialNumber = serialNumber;
        this.isBorrowed = false;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getSerialNumber() { return serialNumber; }
    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }

    @Override
    public String toString() {
        return serialNumber + ": " + title + " by " + author;
    }
}