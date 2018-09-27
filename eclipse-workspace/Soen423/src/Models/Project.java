package Models;

public class Project {
	String projectId;
	String clientName;
	String projectName;
	
	public Project() {
		projectId="";
		clientName="";
		projectName="";
		}
	public Project(String id,String cName, String pName) {
		projectId=id;
		clientName=cName;
		projectName=pName;
		
	}
	public String getProjectId() {
		return projectId;
	}
	public String getClientName() {
		return clientName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProject(String id) {
		projectId = id;
	}
	public void setClientName(String cName) {
		clientName=cName;
	}
	public void setProjectName(String pName) {
		projectName=pName;
	}
	
}