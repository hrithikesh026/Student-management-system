package servicepackage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import doapackage.StudentDAO;
import dtoclasses.StudentDTO;
import dtoclasses.UserDTO;
import pojoclasses.Student;
public class StudentService {
//	1. Insert student
//	2. Delete student by id
//	3. Print all student information with pagination
//	4. Update student information
//	5. Search by name
//	6. Read by name\
//	private List<StudentDTO> studentsList;
	private UserDTO currentUser;
	private StudentDTO getStudentDTOFromEntity(Student student) {
		if(student == null)
			return null;
		return new StudentDTO(student.getId(), student.getName(), student.getAge());
	}
	private List<StudentDTO> getStudentDTOListFromEntityList(List<Student> students){
		List<StudentDTO> result = new ArrayList<>();
		for(Student student: students) {
			result.add(getStudentDTOFromEntity(student));
		}
		return result;
	}
	
	
//	public void addToStudentList(int id, String name, int age) {
//		studentsList.add(new StudentDTO(id,name,age));
//	}
	public StudentService(UserDTO currentUser){
		this.currentUser = currentUser;
	}
	public List<StudentDTO> insertStudents(List<StudentDTO> studentsList) {
		StudentDAO studentDAO = new StudentDAO();
		List<StudentDTO> failedList = new ArrayList<>();
		try {
			failedList = studentDAO.insertStudents(studentsList,currentUser);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		studentsList = new ArrayList<>();
		return failedList;
	}
	
	public StudentDTO findStudentById(int id) throws SQLException {
		StudentDAO studentDAO = new StudentDAO();
		return getStudentDTOFromEntity(studentDAO.getStudentById(id));
	}
	public int deleteStudentById(int id) throws SQLException {
		StudentDAO studentDAO = new StudentDAO();
		int rowsDeleted = studentDAO.deleteStudentById(id);
		return rowsDeleted;
	}
	public List<List<StudentDTO>> getStudentsWithPagination(int pageSize) throws SQLException{
		StudentDAO studentDAO = new StudentDAO();
		List<List<StudentDTO>> paginationResult = new ArrayList<>();
		try {
			List<Student> studentList = studentDAO.getStudentsWithPagination(pageSize, 0);
			int pageCount = 1;
			while(studentList.size()>0){
				paginationResult.add(getStudentDTOListFromEntityList(studentList));
				pageCount++;
				studentList = studentDAO.getStudentsWithPagination(pageSize, (pageCount-1)*pageSize);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paginationResult;
	}
	public int updateStudent(int id, String updatedName, int updatedAge) throws SQLException {
		StudentDAO studentDAO = new StudentDAO();
		StudentDTO updatedStudent = new StudentDTO(id,updatedName, updatedAge);
		int rowsUpdated = studentDAO.updateStudent(id, updatedStudent, currentUser);
		return rowsUpdated;
	}
	public List<StudentDTO> searchByName(String name) throws SQLException {
		StudentDAO studentDAO = new StudentDAO();
		List<Student> studentList = studentDAO.searchByName(name);
		return getStudentDTOListFromEntityList(studentList);
	}
	public List<StudentDTO> readByName() throws SQLException{
		StudentDAO studentDAO = new StudentDAO();
		List<Student> studentList= studentDAO.readByName();
		return getStudentDTOListFromEntityList(studentList);
	}
	public int updateStudentName(int id,String name) throws SQLException {
		if(name == null || name =="") {
			return 0;
		}
		StudentDAO studentDAO = new StudentDAO();
		return studentDAO.updateStudentName(id, name, currentUser);
	}
	public int updateStudentAge(int id, int age) throws SQLException {
		if(age < 3) {
			return 0;
		}
		StudentDAO studentDAO = new StudentDAO();
		return studentDAO.updateStudentAge(id, age, currentUser);
	}
	public int updateStudentId(int id, int newId) throws SQLException {
		if(newId <=0) {
			return 0;
		}
		StudentDAO studentDAO = new StudentDAO();
		return studentDAO.updateStudentId(id, newId, currentUser);
	}
}
