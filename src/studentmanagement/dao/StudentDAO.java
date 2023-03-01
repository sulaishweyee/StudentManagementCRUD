package studentmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import studentmanagement.model.Student;

//used for CRUD operations for Student table in the database
public class StudentDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "1234";
	
	private static final String INSERT_STUDENT_SQL = "INSERT INTO students" + "  (name, email, address) VALUES " +" (?, ?, ?);";
	private static final String SELECT_STUDENT_BY_ID = "select id,name,email,address from students where id =?";
	private static final String SELECT_ALL_STUDENTS = "select * from students";
	private static final String DELETE_STUDENT_SQL = "delete from students where id = ?;";
	private static final String UPDATE_STUDENT_SQL = "update students set name = ?,email= ?, address =? where id = ?;";

	// Get connection with database
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	// Create student
	public void createStudent(Student student) throws SQLException {
		// Establish a connection
		try(Connection connection = getConnection();
				// Create a statement with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)){
			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getEmail());
			preparedStatement.setString(3, student.getAddress());
			// Execute the query
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Update student
	public boolean updateStudent(Student student) throws SQLException {
		boolean rowUpdated;
		// Establish a connection
		try(Connection connection = getConnection();
				// Create a statement with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_SQL)){
			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getEmail());
			preparedStatement.setString(3, student.getAddress());
			preparedStatement.setInt(4, student.getId());
			
			// Execute the query
			rowUpdated = preparedStatement.executeUpdate() > 0;
		} 
		return rowUpdated;
	}
	
	// Select student by id
	public Student selectStudent(int id) throws SQLException {
		Student student = null;
		// Establish a connection
		try(Connection connection = getConnection();
				// Create a statement with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);){
			preparedStatement.setInt(1, id);
			
			// Execute the query
			ResultSet res = preparedStatement.executeQuery();
			
			// Operate the ResultSet
			while(res.next()) {
				String name = res.getString("name");
				String email = res.getString("email");
				String address = res.getString("address");
				student = new Student(id, name, email, address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	// Select all students
	public List<Student> selectAllStudents() throws SQLException {
		List<Student> students = new ArrayList<>();
		// Establish a connection
		try(Connection connection = getConnection();
				// Create a statement with connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS);){
			
			// Execute the query
			ResultSet res = preparedStatement.executeQuery();
			
			// Operate the ResultSet
			while(res.next()) {
				int id = res.getInt("id");
				String name = res.getString("name");
				String email = res.getString("email");
				String address = res.getString("address");
				students.add(new Student(id, name, email, address));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	// Delete student
	public boolean deleteStudent(int id) throws SQLException {
		boolean rowDeleted;
		// Establish a connection
	    try(Connection connection = getConnection();
				// Create a statement with connection object
	    		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_SQL)){
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		return rowDeleted;
		
	}
}
