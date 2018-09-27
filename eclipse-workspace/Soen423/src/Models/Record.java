package Models;

public class Record {
	
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
		recordId =id;
	}
	String firstName;
	String lastName;
	String mailId;
	String recordId;
}