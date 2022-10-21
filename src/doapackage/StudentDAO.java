package doapackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import customexceptions.InvalidStudentObjectException;
import dtoclasses.StudentDTO;
import dtoclasses.UserDTO;
import pojoclasses.Student;
import pojoclasses.User;

public class StudentDAO {
//	1. Insert student
//	2. Delete student by id
//	3. Print all student information with pagination
//	4. Update student information
//	5. Search by name
//	6. Read by name
	private static String URL = "jdbc:mysql://localhost:3306/sampledb";
	private static String PASSWORD = "root@123";
	private static String USERNAME = "root";
	private List<Student> studentList;
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL,USERNAME, PASSWORD ); 
	}
	private Timestamp getCurrentTimestamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	private boolean isValidStudent(StudentDTO student) {
		if(student.getId() <= 0 || student.getName() == null|| student.getName() == "" || student.getAge() <3) {
			return false;
		}else {
			return true;
		}
	}
	private Student getStudentEntityFromResultSet(ResultSet resultSet) throws SQLException {
		String createdBy = resultSet.getString(4);
		Timestamp createdTimestamp = resultSet.getTimestamp(5);
		String updatedBy = resultSet.getString(6);
		Timestamp updatedTimestamp = resultSet.getTimestamp(7);
		return new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), createdBy, createdTimestamp, updatedBy, updatedTimestamp);
	}
	
	
	public int insertStudent(StudentDTO newStudent, UserDTO insertedBy) throws SQLException, InvalidStudentObjectException {
		if(!isValidStudent(newStudent)) {
			String conditions = "id > 0 \n name != null \n name != \"\" age >=3 ";
			throw new InvalidStudentObjectException("Make sure Student object satisfies following conditions\n"+conditions);
		}
		Connection conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
		String sql = "INSERT INTO students_table (std_id, std_name, std_age,created_by, created_timestamp, updated_by, updated_timestamp) VALUES (?, ?, ?, ?, ?,? ?)";
		PreparedStatement statement = null;
		int rowsAffected = 0;
		statement = conn.prepareStatement(sql);
		statement.setInt(1, newStudent.getId());
		statement.setString(2, newStudent.getName());
		statement.setInt(3, newStudent.getAge());
		statement.setString(4, insertedBy.getUsername());
		statement.setTimestamp(5, getCurrentTimestamp());
		statement.setString(6, insertedBy.getUsername());
		statement.setTimestamp(7, getCurrentTimestamp());
		rowsAffected = statement.executeUpdate();
		statement.close();
		conn.close();
		return rowsAffected;
	}
	public List<StudentDTO> insertStudents(List<StudentDTO> newStudents, UserDTO insertedBy) throws SQLException {
		List<StudentDTO> failedList = new ArrayList<>();
		Connection conn = getConnection();
		String sql = "INSERT INTO students_table (std_id, std_name, std_age, created_by, created_timestamp, updated_by, updated_timestamp) VALUES (?, ?, ?, ?, ?,?, ?)";
		PreparedStatement statement = null;
		statement = conn.prepareStatement(sql);
		for(StudentDTO newStudent: newStudents) {
			if(isValidStudent(newStudent)) {
				statement.setInt(1, newStudent.getId());
				statement.setString(2, newStudent.getName());
				statement.setInt(3, newStudent.getAge());
				statement.setString(4, insertedBy.getUsername());
				statement.setTimestamp(5, getCurrentTimestamp());
				statement.setString(6, insertedBy.getUsername());
				statement.setTimestamp(7, getCurrentTimestamp());
				statement.addBatch();
			}
			else {
				failedList.add(newStudent);
			}
		}
		statement.executeBatch();
		conn.close();
		return failedList;
	}
	
	
	public int deleteStudentById(int id) throws SQLException {
		String sql = "delete from students_table where std_id = "+id;
		Statement statement;
		int rowsAffected = 0;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.createStatement();
			rowsAffected = statement.executeUpdate (sql);

	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
        return rowsAffected;
	}
	
	
	
	public Student getStudentById(int id) throws SQLException{
		String sql = "select * from students_table where std_id="+id+";";
		Connection conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
		Statement statement = null;
		Student result = null;
		try {
			
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				result = getStudentEntityFromResultSet(resultSet);
			}
	        
		}catch(SQLException e) {
			throw e;
		}finally {
			if(statement!=null) {
				statement.close();
			}   
			conn.close();
		}
		return result;
	}
	
	public List<Student> getStudentsWithPagination(int pageSize, int startRow) throws SQLException {
		String sql = "select * from students_table limit "+pageSize+"  OFFSET "+startRow+";";
		studentList = new ArrayList<Student>();
		Connection conn = null;
		Statement statement;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()) {
				
				studentList.add(getStudentEntityFromResultSet(resultSet));
			}
	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return studentList;
	}
	public int updateStudent(int id, StudentDTO updatedStudent, UserDTO updatedBy) throws SQLException {
		int updatedId = updatedStudent.getId();
		String updatedName = updatedStudent.getName();
		int updatedAge = updatedStudent.getAge();
		String updatedByUser = updatedBy.getUsername(); 
		Timestamp updatedTimestamp = getCurrentTimestamp();
		Connection conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
		String sql = "UPDATE students_table set  std_name= ?, std_age= ?, updated_by= ?, updated_timestamp= ? where std_id= "+id+";";
		PreparedStatement statement;
		int rowsAffected = 0;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, updatedName);
			statement.setInt(2, updatedAge);
			statement.setString(3, updatedByUser);
			statement.setTimestamp(4, updatedTimestamp);
			rowsAffected = statement.executeUpdate (sql);

	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return rowsAffected;
	}
	public int updateStudentName(int id,String name, UserDTO updatedBy) throws SQLException {
		String sql = "UPDATE students_table set std_name= ?, updated_by= ?, updated_timestamp=? where std_id= "+id;
		String updatedByUser = updatedBy.getUsername(); 
		Timestamp updatedTimestamp = getCurrentTimestamp();
		PreparedStatement statement;
		int rowsAffected = 0;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.prepareStatement(sql);
			statement.setString(1, name);
			statement.setString(2, updatedByUser);
			statement.setTimestamp(3, updatedTimestamp);
			rowsAffected = statement.executeUpdate();

	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return rowsAffected;
	}
	public int updateStudentAge(int id,int age, UserDTO updatedBy) throws SQLException {
		String sql = "UPDATE students_table set std_age= ?, updated_by= ?, updated_timestamp=? where std_id= "+id;
		String updatedByUser = updatedBy.getUsername(); 
		Timestamp updatedTimestamp = getCurrentTimestamp();
		PreparedStatement statement;
		int rowsAffected = 0;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.prepareStatement(sql);
			statement.setInt(1, age);
			statement.setString(2, updatedByUser);
			statement.setTimestamp(3, updatedTimestamp);
			rowsAffected = statement.executeUpdate();

	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return rowsAffected;
	}
	public int updateStudentId(int id,int newId, UserDTO updatedBy) throws SQLException {

		String sql = "UPDATE students_table set std_id= ?, updated_by= ?, updated_timestamp=? where std_id= "+id;
		String updatedByUser = updatedBy.getUsername(); 
		Timestamp updatedTimestamp = getCurrentTimestamp();
		PreparedStatement statement;
		int rowsAffected = 0;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.prepareStatement(sql);
			statement.setInt(1, newId);
			statement.setString(2, updatedByUser);
			statement.setTimestamp(3, updatedTimestamp);
			rowsAffected = statement.executeUpdate();

	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return rowsAffected;
	}
	public List<Student> searchByName(String name)throws SQLException{
		String sql = "select * from students_table where std_name='"+name+"';";
		studentList = new ArrayList<Student>();
		Statement statement;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()) {
				studentList.add(getStudentEntityFromResultSet(resultSet));
			}
	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return studentList;
	}
	public List<Student> readByName()throws SQLException{
		String sql = "select * from students_table  order by std_name;";
		studentList = new ArrayList<Student>();
		Statement statement;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME, PASSWORD );
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()) {
				studentList.add(getStudentEntityFromResultSet(resultSet));
			}
	        statement.close ();   
		}catch(SQLException e) {
			throw e;
		}finally {
			if(conn!=null)
				conn.close();
		}
		return studentList;
	}
}
