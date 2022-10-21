package doapackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dtoclasses.UserDTO;
import pojoclasses.User;

public class UserDAO {
	private static String URL = "jdbc:mysql://localhost:3306/sampledb";
	private static String PASSWORD = "root@123";
	private static String USERNAME = "root";
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL,USERNAME, PASSWORD ); 
	}
	public User insertUser(User newUser) throws SQLException {
		Connection conn = getConnection();
		String sql = "INSERT INTO users_table(username, pass, created_timestamp) VALUES (?, ?, ?)";
		PreparedStatement statement = null;
		statement = conn.prepareStatement(sql);
		statement.setString(1, newUser.getUsername());
		statement.setString(2, newUser.getPass());
		statement.setTimestamp(3, newUser.getCreatedTimestamp());
		if(statement.executeUpdate()>0) {
			return newUser;
		}else {
			return null;
		}
	}
	public User getUser(UserDTO userDTO) throws SQLException {
		String sql = "select * from users_table where username = ? and pass = ?;";
		Connection conn = getConnection();
		PreparedStatement statement = null;
		User user = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, userDTO.getUsername());
			statement.setString(2, userDTO.getPass());
//			System.out.println(statement);1
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getTimestamp(3));
			}
	        
		}catch(SQLException e) {
			throw e;
		}finally {
			if(statement!=null) {
				statement.close();
			}   
			conn.close();
		}
		return user;
	}
}
