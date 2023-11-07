package HOSPITAL_;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagementSystem {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prabhdip", "root", "root");
            initializeDatabase(connection); // Create the 'patients' table

            while (true) {
                System.out.println("1. Patient Registration");
                System.out.println("2. Patient Login");
                System.out.println("3. View Patient Details");
                System.out.println("4. Exit");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        registerPatient(connection);
                        break;
                    case 2:
                        loginPatient(connection);
                        break;
                    case 3:
                        viewPatientDetails(connection);
                        break;
                    case 4:
                        System.out.println("Exiting the system.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDatabase(Connection connection) throws SQLException {
        // Create the 'patients' table if it doesn't exist
        Statement statement = connection.createStatement();
        String createTableQuery = "CREATE TABLE IF NOT EXISTS patients (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL)";
        statement.executeUpdate(createTableQuery);
    }

    private static void registerPatient(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Patient Registration");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Insert the patient data into the database
        String insertQuery = "INSERT INTO patients (name, username, password) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private static void loginPatient(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Patient Login");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check the patient's credentials
        String selectQuery = "SELECT * FROM patients WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private static void viewPatientDetails(Connection connection) throws SQLException {
        System.out.println("Patient Details");

        // Fetch and display patient details from the database
        Statement statement = connection.createStatement();
        String selectQuery = "SELECT * FROM patients";
        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String username = resultSet.getString("username");

            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Username: " + username);
            System.out.println("------------------------");
        }
    }
    private static void viewDoctorDetails(Connection connection) throws SQLException {
        System.out.println("Doctor Details");

        // Fetch and display doctor details from the database
        Statement statement = connection.createStatement();
        String selectQuery = "SELECT * FROM doctors";
        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialization = resultSet.getString("specialization");

            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialization: " + specialization);
            System.out.println("------------------------");
        }
    }


}
