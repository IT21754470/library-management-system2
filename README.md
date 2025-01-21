# Library Management System

## Description

This Library Management System is a Java-based application designed to help librarians and library staff manage their collection of books and magazines, as well as track user borrowing activities. The system provides an interactive command-line interface for easy management of library resources and user interactions.

## Features

- Create and manage library items (books and magazines)
- Add and manage library users
- Allow users to borrow and return items
- Persistent data storage using text files
- Display all library items
- Error handling and input validation

## Technologies Used

- Java
- File I/O for data persistence

## Setup and Installation

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Clone this repository to your local machine.
3. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
4. Compile the Java files.

## Usage

1. Run the `Main` class to start the application.
2. You will be presented with a menu of options:
   - Create new item
   - Create new user
   - Borrow item
   - Return item
   - Exit
3. Follow the on-screen prompts to perform various actions.
4. When exiting, all data will be automatically saved to files.

## File Structure

- `Main.java`: Contains the main program logic and user interface.
- `Library.java`: Manages the collection of items and users.
- `model/LibraryItem.java`: Abstract class for library items.
- `model/Book.java`: Represents a book in the library.
- `model/Magazine.java`: Represents a magazine in the library.
- `model/User.java`: Represents a library user.

## Data Persistence

The system uses three text files for data storage:
- `users.txt`: Stores user information
- `items.txt`: Stores library item information
- `borrowed_items.txt`: Stores information about borrowed items

## Future Enhancements

- Implement a graphical user interface (GUI)
- Add search functionality for items and users
- Implement due dates and fine calculation for overdue items
- Add more detailed reporting features

## Contributing

Contributions to improve the Library Management System are welcome. Please feel free to fork the repository, make changes, and submit pull requests.

## License

This project is open source and available under the [MIT License](LICENSE).
 
