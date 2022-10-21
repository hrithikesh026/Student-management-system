package servicepackage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import doapackage.UserDAO;
import dtoclasses.UserDTO;
import pojoclasses.User;

public class UserService {
	private UserDTO getUserDTOFromEntity(User user) {
		return new UserDTO(user.getUsername(), user.getPass());
	}
	public UserDTO addUser(String username, String password) {
		Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		try {
			return getUserDTOFromEntity(new UserDAO().insertUser(new User(username, password, currentTimestamp)));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public UserDTO getUser(String username, String password) {
		try {
			return getUserDTOFromEntity(new UserDAO().getUser(new UserDTO(username, password)));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
