package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dtoclasses.StudentDTO;
import dtoclasses.UserDTO;
import pojoclasses.Student;
import utils.KeyBoardUtil;
import servicepackage.StudentService;
import servicepackage.UserService;

public class StudentController {
	private UserDTO currentUser;
	private StudentService getStudentServiceObject() {
		return new StudentService(currentUser);
	}
	
	public StudentController(UserDTO currentUser){
		this.currentUser = currentUser;
	}
	
	public List<StudentDTO> insertStudents(List<StudentDTO> studentDtos) {
		StudentService studentService = getStudentServiceObject();
		List<StudentDTO> failedToInsertList = new ArrayList<>();
		failedToInsertList = studentService.insertStudents(studentDtos);
		return failedToInsertList;
	}
	public int deleteStudent(int id) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.deleteStudentById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public List<List<StudentDTO>> getAllStudentsWithPagination(int pageSize) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.getStudentsWithPagination(pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	public int updateStudentAge(int id, int newAge) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.updateStudentAge(id, newAge);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public int updateStudentId(int id,int newId) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.updateStudentId(id, newId);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public int updateStudentName(int id, String newName) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.updateStudentName(id, newName);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<StudentDTO> searchStudentsByName(String name) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.searchByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<StudentDTO> readStudentsByName() {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.readByName();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public StudentDTO findStudentById(int id) {
		StudentService studentService = getStudentServiceObject();
		try {
			return studentService.findStudentById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}