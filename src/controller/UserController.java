package controller;

import dtoclasses.UserDTO;
import servicepackage.UserService;

public class UserController {
	private UserService getUserServiceObject() {
		return new UserService();
	}
	public UserDTO getUser(String username, String password) {
		UserService userService = getUserServiceObject();
		return userService.getUser(username, password);
	}
	public UserDTO addUser(String username, String password) {
		UserService userService = getUserServiceObject();
		return userService.addUser(username, password);
	}
}
