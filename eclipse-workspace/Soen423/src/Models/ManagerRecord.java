package Models;

import java.io.Serializable;

import Models.Project;

public class ManagerRecord extends Record implements Serializable {

	private String location;
	private String managerId;
	private Project project;

	public ManagerRecord(String firstName, String lastName, String employeeId, String mailId, String recordId) {
		super(firstName, lastName, mailId, recordId);
		// TODO Auto-generated constructor stub
	}

	public ManagerRecord(String firstName, String lastName, String managerId, String mailId, String recordId,
			String location, Project project) {
		super(firstName, lastName, mailId, recordId);
		this.location = location;
		this.project = project;
		this.managerId = managerId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String assign(String field, String newVal) {
		switch (field) {
		case "mailId":
			setMailId(newVal);
			return "The mail id was edited";
		case "location":
			setLocation(newVal);
			return "The Location was edited";
		case "projectId":
			project.setProjectId(newVal);
			return "The project id was edited";
		case "clientName":
			project.setClientName(newVal);
			return "The client name was edited";
		case "projectName":
			project.setProjectName(newVal);
			return "The project name was edited";
		default:
			return "This is illigal operation.";

		}
	}
}
