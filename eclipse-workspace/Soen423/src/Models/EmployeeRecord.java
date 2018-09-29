package Models;

import java.io.Serializable;

public class EmployeeRecord extends Record implements Serializable{
	private int employeeId;	
	private String projectId;
	
	public EmployeeRecord(String firstName, String lastName, String mailId, String recordId) {
		super(firstName, lastName, mailId, recordId);
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeRecord(String firstName, String lastName,int employeeId, String mailId, String recordId, String projectId) {
		super(firstName, lastName, mailId, recordId);
		this.projectId = projectId;
		this.employeeId = employeeId;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
			
}
