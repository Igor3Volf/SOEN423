package Models;
import Models.Project;
public class ManagerRecord extends Record{

	public ManagerRecord(String firstName, String lastName, String mailId, String recordId) {
		super(firstName, lastName, mailId, recordId);
		// TODO Auto-generated constructor stub
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

	public ManagerRecord(String firstName, String lastName, String mailId, String recordId, String location, Project project) {
		super(firstName, lastName, mailId, recordId);
		this.location = location;
		this.project = project;
	}

	String location;
	Project project;
	
	
}
