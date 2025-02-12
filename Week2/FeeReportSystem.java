import java.sql.*;
import java.util.Scanner;

public class FeeReportSystem {
	private static final String URL = "jdbc:mysql://localhost:3306/feereport";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	private static Connection con;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			while (true) {
				System.out.println("\n1. Admin Login\n2. Exit");
				System.out.print("Enter choice: ");
				int choice = scanner.nextInt();
				if (choice == 1)
					performAdminLogin();
				else
					break;
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void performAdminLogin() throws SQLException {
		System.out.print("Username: ");
		String username = scanner.next();
		System.out.print("Password: ");
		String password = scanner.next();
		if (checkAdminCredentials(username, password))
			displayAdminMenu();
		else
			System.out.println("Invalid credentials!");
	}

	private static boolean checkAdminCredentials(String username, String password) throws SQLException {
		PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
		ps.setString(1, username);
		ps.setString(2, password);
		return ps.executeQuery().next();
	}

	private static void displayAdminMenu() throws SQLException {
		while (true) {
			System.out.println("\n1. Add Accountant\n2. View Accountants\n3. Manage Students\n4. Logout");
			System.out.print("Enter choice: ");
			switch (scanner.nextInt()) {
			case 1 -> createAccountant();
			case 2 -> displayTable("accountant");
			case 3 -> handleStudentManagement();
			case 4 -> {
				return;
			}
			default -> System.out.println("Invalid choice!");
			}
		}
	}

	private static void createAccountant() throws SQLException {
		System.out.print("Name: ");
		String name = scanner.next();
		System.out.print("Email: ");
		String email = scanner.next();
		System.out.print("Phone: ");
		String phone = scanner.next();
		System.out.print("Password: ");
		String password = scanner.next();
		executeDatabaseUpdate("INSERT INTO accountant(name,email,phone,password) VALUES(?,?,?,?)", name, email, phone,
				password);
	}

	private static void handleStudentManagement() throws SQLException {
		while (true) {
			System.out.println("\n1. Add Student\n2. View Students\n3. Check Due Fees\n4. Back");
			System.out.print("Enter choice: ");
			switch (scanner.nextInt()) {
			case 1 -> createStudent();
			case 2 -> displayTable("student");
			case 3 -> showDueFees();
			case 4 -> {
				return;
			}
			default -> System.out.println("Invalid choice!");
			}
		}
	}

	private static void createStudent() throws SQLException {
		System.out.print("Name: ");
		String name = scanner.next();
		System.out.print("Email: ");
		String email = scanner.next();
		System.out.print("Course: ");
		String course = scanner.next();
		System.out.print("Total Fee: ");
		double fee = scanner.nextDouble();
		System.out.print("Paid Fee: ");
		double paid = scanner.nextDouble();
		System.out.print("Address: ");
		String address = scanner.next();
		System.out.print("Phone: ");
		String phone = scanner.next();
		executeDatabaseUpdate(
				"INSERT INTO student(name,email,course,fee,paid,due,address,phone) VALUES(?,?,?,?,?,?,?,?)", name,
				email, course, fee, paid, (fee - paid), address, phone);
	}

	private static void displayTable(String tableName) throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + tableName);
		while (rs.next()) {
			System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
		}
	}

	private static void showDueFees() throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("SELECT name, due FROM student WHERE due > 0");
		while (rs.next()) {
			System.out.println("Name: " + rs.getString(1) + " | Due: " + rs.getDouble(2));
		}
	}

	private static void executeDatabaseUpdate(String query, Object... params) throws SQLException {
		PreparedStatement ps = con.prepareStatement(query);
		for (int i = 0; i < params.length; i++)
			ps.setObject(i + 1, params[i]);
		System.out.println(ps.executeUpdate() > 0 ? "Operation successful!" : "Error!");
	}
}
