package models.request;

import models.Project;

public class CreateMRecordRequest extends Request{
	private static final long serialVersionUID = 1L;
	
	private String managerID;
	private String firstName;
	private String lastName;
	private int employeeID;
	private String mailID;
	private Project project;
	private String location;
	
	public CreateMRecordRequest(String managerID, String firstName, String lastName,
			int employeeID, String mailID, Project project, String location) {
		super();
		this.managerID = managerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
		this.mailID = mailID;
		this.project = project;
		this.location = location;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
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

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getMailID() {
		return mailID;
	}

	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}

