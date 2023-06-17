package p1;

import java.sql.*;
import java.util.Scanner;
import java.sql.Date;

public class PoisePMSProgram {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/poisepms";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Micayo@1";

    private Connection connection;

    public PoisePMSProgram() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle connection failure
        }
    }

    // Method to read and display all projects and associated people
    public void displayProjectsAndPeople() {
        try {
            Statement statement = connection.createStatement();

            // Retrieve and display project data
            ResultSet projectResultSet = statement.executeQuery("SELECT * FROM Projects");
            System.out.println("Projects:");
            while (projectResultSet.next()) {
                int project_id = projectResultSet.getInt("project_id");
                String project_name = projectResultSet.getString("project_name");
                String project_status = projectResultSet.getString("project_status");
                int architect_id = projectResultSet.getInt("architect_id");
                int customer_id = projectResultSet.getInt("customer_id");

                System.out.println("Project ID: " + project_id);
                System.out.println("Project Name: " + project_name);
                System.out.println("Status: " + project_status);
                System.out.println("Architect ID: " + architect_id);
                System.out.println("Customer ID: " + customer_id);
                System.out.println("-----------------------");
            }
            projectResultSet.close();

            // Retrieve and display employee data
            ResultSet employeeResultSet = statement.executeQuery("SELECT * FROM Employees");
            System.out.println("Employees:");
            while (employeeResultSet.next()) {
                int employee_id = employeeResultSet.getInt("employee_id");
                String employee_name = employeeResultSet.getString("employee_name");
                String employee_role = employeeResultSet.getString("employee_role");

                System.out.println("Employee ID: " + employee_id);
                System.out.println("Employee Name: " + employee_name);
                System.out.println("Role: " + employee_role);
                System.out.println("-----------------------");
            }
            employeeResultSet.close();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to update an existing project in the database
    public void updateProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter project number or name: ");
        String userInput = scanner.nextLine();

        try {
            Statement statement = connection.createStatement();

            // Retrieve project information from the database based on user input
            ResultSet projectResultSet = statement.executeQuery("SELECT * FROM Projects WHERE project_number = '" + userInput + "' OR project_name = '" + userInput + "'");
            if (projectResultSet.next()) {
                int project_id = projectResultSet.getInt("project_id");

                // Prompt and capture updated project information from the user
                System.out.print("Enter updated project name: ");
                String updatedProjectName = scanner.nextLine();
                System.out.print("Enter updated project status: ");
                String updatedProjectStatus = scanner.nextLine();

                // Update project data in the Projects table
                String updateProjectQuery = "UPDATE Projects SET project_name = '" + updatedProjectName + "', project_status = '" + updatedProjectStatus + "' WHERE project_id = " + project_id;
                statement.executeUpdate(updateProjectQuery);

                System.out.println("Project updated successfully!");
            } else {
                System.out.println("Project not found!");
            }
            projectResultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to delete a project and associated people from the database
    public void deleteProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter project number or name: ");
        String userInput = scanner.nextLine();

        try {
            Statement statement = connection.createStatement();

            // Retrieve project information from the database based on user input
            ResultSet projectResultSet = statement.executeQuery("SELECT * FROM Projects WHERE project_number = '" + userInput + "' OR project_name = '" + userInput + "'");
            if (projectResultSet.next()) {
                int project_id = projectResultSet.getInt("project_id");

                // Delete project data from the Projects table
                String deleteProjectQuery = "DELETE FROM Projects WHERE project_id = " + project_id;
                statement.executeUpdate(deleteProjectQuery);

                // Delete associated people data from the appropriate tables
                String deleteEmployeesQuery = "DELETE FROM Employees WHERE project_id = " + project_id;
                statement.executeUpdate(deleteEmployeesQuery);

                System.out.println("Project deleted successfully!");
            } else {
                System.out.println("Project not found!");
            }

            projectResultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to finalize a project by marking it as "finalized" and adding completion date
    public void finalizeProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter project number or name: ");
        String userInput = scanner.nextLine();

        try {
            Statement statement = connection.createStatement();

            // Retrieve project information from the database based on user input
            ResultSet projectResultSet = statement.executeQuery("SELECT * FROM Projects WHERE project_number = '" + userInput + "' OR project_name = '" + userInput + "'");
            if (projectResultSet.next()) {
                int project_id = projectResultSet.getInt("project_id");

                // Update project status and completion date in the Projects table
                String finalizeProjectQuery = "UPDATE Projects SET project_status = 'finalized', completion_date = CURDATE() WHERE project_id = " + project_id;
                statement.executeUpdate(finalizeProjectQuery);

                System.out.println("Project finalized successfully!");
            } else {
                System.out.println("Project not found!");
            }

            projectResultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to find all projects that still need to be completed
    public void findIncompleteProjects() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Projects WHERE project_status <> 'finalized'");

            // Process and display incomplete project data
            System.out.println("Incomplete Projects:");
            while (resultSet.next()) {
                int project_id = resultSet.getInt("project_id");
                String project_name = resultSet.getString("project_name");
                String project_status = resultSet.getString("project_status");
                Date completion_date = resultSet.getDate("completion_date");

                System.out.println("Project ID: " + project_id);
                System.out.println("Project Name: " + project_name);
                System.out.println("Status: " + project_status);
                System.out.println("Completion Date: " + completion_date);
                System.out.println("-----------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to find all projects that are past the due date
    public void findPastDueProjects() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Projects WHERE project_status <> 'finalized' AND due_date < CURDATE()");

            // Process and display past due project data
            System.out.println("Past Due Projects:");
            while (resultSet.next()) {
                int project_id = resultSet.getInt("project_id");
                String project_name = resultSet.getString("project_name");
                String project_status = resultSet.getString("project_status");
                Date completion_date = resultSet.getDate("completion_date");

                System.out.println("Project ID: " + project_id);
                System.out.println("Project Name: " + project_name);
                System.out.println("Status: " + project_status);
                System.out.println("Completion Date: " + completion_date);
                System.out.println("-----------------------");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to find and select a project by project number or name
    public void findProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter project number or name: ");
        String userInput = scanner.nextLine();

        try {
            Statement statement = connection.createStatement();

            // Retrieve project information from the database based on user input
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Projects WHERE project_number = '" + userInput + "' OR project_name = '" + userInput + "'");

            // Process and display project data
            if (resultSet.next()) {
                int project_id = resultSet.getInt("project_id");
                String project_name = resultSet.getString("project_name");
                String project_status = resultSet.getString("project_status");
                Date completion_date = resultSet.getDate("completion_date");

                System.out.println("Project ID: " + project_id);
                System.out.println("Project Name: " + project_name);
                System.out.println("Status: " + project_status);
                System.out.println("Completion Date: " + completion_date);
            } else {
                System.out.println("Project not found!");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to add a new project to the database
    public void addNewProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter project status: ");
        String projectStatus = scanner.nextLine();

        try {
            Statement statement = connection.createStatement();

            // Insert project data into the Projects table
            String addProjectQuery = "INSERT INTO Projects (project_name, project_status) VALUES ('" + projectName + "', '" + projectStatus + "')";
            statement.executeUpdate(addProjectQuery);

            System.out.println("New project added successfully!");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Main menu method
    public static void main(String[] args) {
        PoisePMSProgram program = new PoisePMSProgram();
        Scanner scanner = new Scanner(System.in);

        // Implement the program menu and user interface
        boolean exit = false;
        while (!exit) {
            System.out.println("===== PoisePMS Program Menu =====");
            System.out.println("1. Display all projects and associated people");
            System.out.println("2. Add a new project");
            System.out.println("3. Update an existing project");
            System.out.println("4. Delete a project and associated people");
            System.out.println("5. Finalize a project");
            System.out.println("6. Find all projects that still need to be completed");
            System.out.println("7. Find all projects that are past the due date");
            System.out.println("8. Find and select a project");
            System.out.println("9. Exit program");

            System.out.print("Enter your choice (1-9): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    program.displayProjectsAndPeople();
                    break;
                case 2:
                    program.addNewProject();
                    break;
                case 3:
                    program.updateProject();
                    break;
                case 4:
                    program.deleteProject();
                    break;
                case 5:
                    program.finalizeProject();
                    break;
                case 6:
                    program.findIncompleteProjects();
                    break;
                case 7:
                    program.findPastDueProjects();
                    break;
                case 8:
                    program.findProject();
                    break;
                case 9:
                    exit = true;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 9.");
            }

            System.out.println();
        }

        scanner.close();
    }
}
