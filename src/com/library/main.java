package com.library;

import com.library.model.*;
import java.util.Scanner;
import java.io.*;
import java.util.List;

public class main {
    private static library library = new library();
    private static Scanner scanner = new Scanner(System.in);
    private static final String USERS_FILE = "users.txt";
    private static final String ITEMS_FILE = "items.txt";
    private static final String BORROWED_ITEMS_FILE = "borrowed_items.txt";

    public static void main(String[] args) {
        loadData();
        displayAllItems();

        while (true) {
            try {
                displayMainMenu();
                int choice = getValidIntInput();

                switch (choice) {
                    case 1:
                        createItem();
                        break;
                    case 2:
                        createUser();
                        break;
                    case 3:
                        borrowItem();
                        break;
                    case 4:
                        returnItem();
                        break;
                    case 5:
                        saveData();
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nEnter the main option:");
        System.out.println("1. Create new item");
        System.out.println("2. Create new user");
        System.out.println("3. Borrow item");
        System.out.println("4. Return item");
        System.out.println("5. Exit");
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void createItem() {
        try {
            System.out.println("Enter item type (book/magazine):");
            String type = scanner.nextLine().trim().toLowerCase();
            System.out.println("Enter title:");
            String title = scanner.nextLine().trim();
            System.out.println("Enter author:");
            String author = scanner.nextLine().trim();
            System.out.println("Enter serial number:");
            String serialNumber = scanner.nextLine().trim();

            if (title.isEmpty() || author.isEmpty() || serialNumber.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            LibraryItem newItem;
            if (type.equals("book")) {
                newItem = new Book(title, author, serialNumber);
            } else if (type.equals("magazine")) {
                newItem = new Magazine(title, author, serialNumber);
            } else {
                throw new IllegalArgumentException("Invalid item type. Please enter 'book' or 'magazine'.");
            }

            library.addItem(newItem);
            System.out.println("Item added successfully.");
        } catch (Exception e) {
            System.out.println("Error creating item: " + e.getMessage());
        }
    }

    private static void createUser() {
        try {
            System.out.println("Enter user name:");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("User name cannot be empty.");
            }
            User newUser = new User(name);
            library.addUser(newUser);
            System.out.println("User added successfully.");
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private static void borrowItem() {
        try {
            List<User> users = library.getUsers();
            if (users.isEmpty()) {
                System.out.println("No users available. Please create a user first.");
                return;
            }

            System.out.println("Which user is going to borrow the item?");
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + ". " + users.get(i).getName());
            }
            int userIndex = getValidIntInput();

            if (userIndex < 0 || userIndex >= users.size()) {
                throw new IllegalArgumentException("Invalid user selection.");
            }

            User user = users.get(userIndex);

            System.out.println("Enter the serial number of the item:");
            String serialNumber = scanner.nextLine().trim();

            LibraryItem item = library.findItem(serialNumber);
            if (item == null) {
                throw new IllegalArgumentException("Item not found.");
            }

            if (item.isBorrowed()) {
                System.out.println("Item " + item.getSerialNumber() + " is already borrowed.");
            } else {
                user.borrowItem(item);
                System.out.println("Item " + item.getSerialNumber() + " borrowed by " + user.getName());
            }
        } catch (Exception e) {
            System.out.println("Error borrowing item: " + e.getMessage());
        }
    }

    private static void returnItem() {
        try {
            List<User> users = library.getUsers();
            if (users.isEmpty()) {
                System.out.println("No users available.");
                return;
            }

            System.out.println("Which user is returning the item?");
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + ". " + users.get(i).getName());
            }
            int userIndex = getValidIntInput();

            if (userIndex < 0 || userIndex >= users.size()) {
                throw new IllegalArgumentException("Invalid user selection.");
            }

            User user = users.get(userIndex);

            List<LibraryItem> borrowedItems = user.getBorrowedItems();
            if (borrowedItems.isEmpty()) {
                System.out.println("This user has no borrowed items.");
                return;
            }

            System.out.println("Enter the serial number of the item to return:");
            String serialNumber = scanner.nextLine().trim();

            LibraryItem itemToReturn = borrowedItems.stream()
                    .filter(item -> item.getSerialNumber().equals(serialNumber))
                    .findFirst()
                    .orElse(null);

            if (itemToReturn == null) {
                throw new IllegalArgumentException("Item not found in user's borrowed items.");
            }

            user.returnItem(itemToReturn);
            System.out.println("Item " + itemToReturn.getSerialNumber() + " returned by " + user.getName());
        } catch (Exception e) {
            System.out.println("Error returning item: " + e.getMessage());
        }
    }

    private static void saveData() {
        try {
            // Save users
            try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
                for (User user : library.getUsers()) {
                    writer.println(user.getName());
                }
            }
            System.out.println("User list saved successfully.");

            // Save library items
            try (PrintWriter writer = new PrintWriter(new FileWriter(ITEMS_FILE))) {
                for (LibraryItem item : library.getItems()) {
                    writer.println(item.getSerialNumber() + "," + item.getTitle() + "," + item.getAuthor() + "," + (item instanceof Book ? "Book" : "Magazine"));
                }
            }
            System.out.println("Library item list saved to the file successfully.");

            // Save borrowed items
            try (PrintWriter writer = new PrintWriter(new FileWriter(BORROWED_ITEMS_FILE))) {
                for (User user : library.getUsers()) {
                    for (LibraryItem item : user.getBorrowedItems()) {
                        writer.println(user.getName() + "," + item.getSerialNumber());
                    }
                }
            }
            System.out.println("Borrowed items written to file successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred while saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        try {
            // Load users
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    library.addUser(new User(line.trim()));
                }
            }

            // Load library items
            try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        LibraryItem item = parts[3].equals("Book") ?
                                new Book(parts[1], parts[2], parts[0]) :
                                new Magazine(parts[1], parts[2], parts[0]);
                        library.addItem(item);
                    }
                }
            }

            // Load borrowed items
            try (BufferedReader reader = new BufferedReader(new FileReader(BORROWED_ITEMS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        User user = library.findUser(parts[0]);
                        LibraryItem item = library.findItem(parts[1]);
                        if (user != null && item != null) {
                            user.borrowItem(item);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while loading data: " + e.getMessage());
        }
    }

    private static void displayAllItems() {
        System.out.println("List of all library items:");
        List<LibraryItem> items = library.getItems();
        for (LibraryItem item : items) {
            System.out.println(item.getSerialNumber() + " " + item.getAuthor() + " " + item.getTitle());
        }
        System.out.println();
    }
}

