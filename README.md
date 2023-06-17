# Project-management-system
# PoisePMSProgram is found on src folder

This Java program provides functionality to manage projects and associated people in the PoisePMS database. It allows users to perform various operations such as displaying projects and people, adding new projects, updating existing projects, deleting projects and associated people, finalizing projects, and finding projects based on different criteria.

## Prerequisites
- Java Development Kit (JDK)
- MySQL Connector/J library

## Setup
1. Ensure that you have the Java Development Kit (JDK) installed on your system.
2. Download and install the MySQL Connector/J library.
3. Clone or download the source code files for the PoisePMSProgram.

## Configuration
1. Open the `PoisePMSProgram.java` file in a text editor.
2. Modify the following variables to match your MySQL database configuration:
   - `DB_URL`: The URL of the MySQL database, including the port number and database name.
   - `DB_USERNAME`: The username used to connect to the MySQL database.
   - `DB_PASSWORD`: The password used to connect to the MySQL database.

## Usage
1. Compile the Java source code using the Java compiler.
2. Run the compiled program using the Java Virtual Machine (JVM).
3. Follow the on-screen instructions to interact with the program.
4. Choose a menu option by entering the corresponding number.

### Menu Options
1. Display all projects and associated people: Retrieve and display information about all projects and the people involved.
2. Add a new project: Add a new project to the database by providing the necessary details.
3. Update an existing project: Update the details of an existing project based on project number or name.
4. Delete a project and associated people: Delete a project and its associated people from the database based on project number or name.
5. Finalize a project: Mark a project as "finalized" and add the completion date.
6. Find all projects that still need to be completed: Retrieve and display information about all projects that are not yet finalized.
7. Find all projects that are past the due date: Retrieve and display information about all projects that have a due date earlier than the current date.
8. Find and select a project by project number or name: Search for a project based on project number or name and display its details.
9. Exit: Quit the program.

## Closing the Program
To close the program and release resources, choose the "9. Exit" option from the main menu. The program will automatically close the database connection and terminate.

## Note
Make sure to close the program properly to ensure the database connection is closed. If the program terminates abruptly or encounters an error, the `closeConnection()` method can be called manually to close the connection.

```java
program.closeConnection();
```

## License
This project is licensed under the [MIT License](LICENSE).
