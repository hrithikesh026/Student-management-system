package Main;

import java.sql.SQLException;
import java.util.Scanner;

import sessions.UserSession;
import utils.KeyBoardUtil;
public class Main {
	static void sop(Object obj) {
		System.out.println(obj);
	}
	static void login() {
		KeyBoardUtil  kbu = new KeyBoardUtil();
		String username = kbu.readString("Enter Username");
		String password = kbu.readString("Enter Password");
		UserSession  userSession = UserSession.getSession(username, password);
		userSession.startSession();
	}
	public static void main(String args[]) throws SQLException {
		Scanner s = new Scanner(System.in);
		boolean exit = false;
		while(!exit) {
			sop("Enter Your Choice");
			sop("1. User Login");
			sop("2. Exit");
			int choice = s.nextInt();
			switch(choice) {
			case 1: login();break;
			case 2: exit = true;
			default: sop("Invalid input");
			}
		}
	}
}
