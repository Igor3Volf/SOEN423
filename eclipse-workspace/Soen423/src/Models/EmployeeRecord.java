package Models;

public class EmployeeRecord extends Record {

	public EmployeeRecord(String firstName, String lastName, String mailId, String recordId) {
		super(firstName, lastName, mailId, recordId);
		// TODO Auto-generated constructor stub
	}
	public EmployeeRecord(String firstName, String lastName, String mailId, String recordId, String projectId) {
		super(firstName, lastName, mailId, recordId);
		this.projectId = projectId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	String projectId;

	
			
}
