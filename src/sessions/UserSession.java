package sessions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pojoclasses.Student;
import servicepackage.StudentService;
import utils.KeyBoardUtil;
import controller.StudentController;
import controller.UserController;
import dtoclasses.StudentDTO;
import dtoclasses.UserDTO;

public class UserSession {
	private String username;
	private String password;
	private UserDTO userDTO;
	private void sop(Object obj) {
		System.out.println(obj);
	}
	private StudentController getController() {
		return new StudentController(userDTO);
	}
	private void insertStudents() {
		KeyBoardUtil kbu = new KeyBoardUtil();
		StudentController controller = getController();
		List<StudentDTO> studentsList = new ArrayList<>();
		List<StudentDTO> failedToInsertList = new ArrayList<>();
		int numberOfStudents = kbu.readInt("Enter Number of Students to add");
		for(int i=1;i<=numberOfStudents;i++) {
			sop("Enter Details of Student "+i);
			int id = kbu.readInt("Enter student id");

			if(controller.findStudentById(id) != null) {
				sop("Student with id "+id+" already exists in the database");
				continue;
			}
			
			String name = kbu.readString("Enter Student name");
			int age = kbu.readInt("Enter Student Age");
			studentsList.add(new StudentDTO(id,name,age));
		}
		failedToInsertList = controller.insertStudents(studentsList);
		if(failedToInsertList.size()>0) {
			sop("Following Records Could not be inserted.\nMake Sure the details are Valid");
			for(StudentDTO student: failedToInsertList) {
				sop(student);
			}
		}else {
			sop("Records inserted successfully");
		}
	}
	private void deleteStudent() {
		KeyBoardUtil kbu = new KeyBoardUtil();
		StudentController controller = getController();
		int id = kbu.readInt("Enter student id");

		int rows = controller.deleteStudent(id);
		if(rows == 1) {
			sop("Deleted student with id "+id);
		}else {
			sop("No student with id "+id);
		}
	}
	private void getAllStudentsWithPagination() {
		KeyBoardUtil kbu = new KeyBoardUtil();
		StudentController controller = getController();
		int pageSize = kbu.readInt("Enter Page Size");
		List<List<StudentDTO>> pagedStudentsList = controller.getAllStudentsWithPagination(pageSize);
		int pageCount=1;
		for(List<StudentDTO> studentList: pagedStudentsList) {
			sop("Page "+pageCount);
			pageCount++;
			for(StudentDTO student: studentList) {
				sop(student);
			}
			sop("");
		}
	}
	private void updateStudent() {
		KeyBoardUtil kbu = new KeyBoardUtil();
		StudentController controller = getController();
		int id = kbu.readInt("Enter student id to update");
		if(controller.findStudentById(id) == null) {
			sop("No student with id "+id);
			return;
		}
		int updatedId = kbu.readInt("Enter New Id (Enter 0 to skip)");
		if(updatedId>0) {
				controller.updateStudentId(id, updatedId);
				id = updatedId;
				sop("Student Id Updated");
		}
		String updatedName = kbu.readString("Enter updated student name (Press Enter to skip)");
		if(!updatedName.isEmpty()) {
				controller.updateStudentName(id, updatedName);
				sop("Student name Updated");
			
		}
		int updatedAge = kbu.readInt("Enter updated student age (Enter 0 to skip)");
		if(updatedAge>0) {
				controller.updateStudentAge(id, updatedAge);
				sop("Student age Updated");
		}
	}
	private void searchStudentsByName() {
		KeyBoardUtil kbu = new KeyBoardUtil();
		StudentController controller = getController();
		String searchName = kbu.readString("Enter student name");

		List<StudentDTO> studentList = controller.searchStudentsByName(searchName);
		if(studentList.size()>0) {
			sop("List of students with name "+searchName);
			for(StudentDTO s: studentList) {
				sop(s);
			}
		}else {
			sop("No records found");
		}
	}
	private void readStudentsByName() {
	
		StudentController controller = getController();
			List<StudentDTO> studentList = controller.readStudentsByName();
			if(studentList.size()>0) {
				sop("List of students");
				for(StudentDTO s: studentList) {
					sop(s);
				}
			}else {
				sop("No records found");
			}
	}
	private UserSession() {
		
	}
	private UserSession(UserDTO user) {
		this.username = user.getUsername();
		this.password = user.getPass();
		this.userDTO = user;
	}
	public static UserSession getSession(String username, String password) {
		UserController controller =new UserController();
		UserDTO user = controller.getUser(username, password);
		if(user == null) {
			user = controller.addUser(username, password);
		}
		return new UserSession(user);
	}
	
	public void startSession() {
		Scanner s = new Scanner(System.in);
		while(true) {
			sop("Enter Your Choice");
			sop("1. Insert students");
			sop("2. Delete student by id");
			sop("3. Print all student information with pagination");
			sop("4. Update student information");
			sop("5. Search by name");
			sop("6. Sort by name");
			sop("7. Exit");
			int choice = s.nextInt();
			boolean exit = false;
			switch(choice) {
				case 1: insertStudents();break;
				case 2: deleteStudent();break;
				case 3: getAllStudentsWithPagination();break;
				case 4: updateStudent();break;
				case 5: searchStudentsByName();break;
				case 6: readStudentsByName();break;
				case 7: exit = true; break;
				default: sop("Invalid Input");
			}
			if(exit) {
				break;
			}
		}
		s.close();
		sop("Session ended");
	}
	
}

