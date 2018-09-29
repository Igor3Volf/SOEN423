package Models;

import java.io.Serializable;

public class Record implements Serializable{
	
	public String getRecordId() {
		return recordId;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}	
	public Record(String firstName, String lastName, String mailId, String id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailId = mailId;
		recordId=id + String.valueOf(count++);
	}
	private String firstName;
	private String lastName;
	private String mailId;
	private String recordId;
	private static int count = 0;
}
