package dtoclasses;

public class StudentDTO {
	private int stdId;
	private String stdName;
	private int stdAge;
	public StudentDTO(int stdId, String stdName, int stdAge) {
		this.stdId= stdId;
		this.stdName = stdName;
		this.stdAge = stdAge;
	}
	public String toString() {
		return "[stdId="+stdId+", stdName="+stdName+", stdAge="+stdAge+"]";
	}
	public int getId() {
		return stdId;
	}
	public String getName() {
		return stdName;
	}
	public int getAge() {
		return stdAge;
	}
}
