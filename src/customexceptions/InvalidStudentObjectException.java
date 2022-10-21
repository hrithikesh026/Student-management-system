package customexceptions;

public class InvalidStudentObjectException extends RuntimeException{
	public InvalidStudentObjectException(String message){
		super(message);
	}
}
