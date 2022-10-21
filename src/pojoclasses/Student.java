package pojoclasses;

import java.sql.Timestamp;

public class Student {
	private int stdId;
	private String stdName;
	private int stdAge;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdTimestamp; 
	private Timestamp updatedTimestamp; 
	public Student(int stdId, String stdName, int stdAge, String createdBy, Timestamp createdTimestamp, String updatedBy, Timestamp updatedTimestamp) {
		this.stdId= stdId;
		this.stdName = stdName;
		this.stdAge = stdAge;
		this.createdBy = createdBy;
		this.createdTimestamp = createdTimestamp;
		this.updatedBy = updatedBy;
		this.updatedTimestamp = updatedTimestamp;
	}
	public String toString() {
		String temp = "[stdId=%d, stdName=%s, stdAge=%d, createdBy=%s, createdTimestamp=%s, updatedBy=%s, updatedTimestamp=%s]";
		return String.format(temp, stdId,stdName,stdAge,createdBy,createdTimestamp,updatedBy,updatedTimestamp);
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
