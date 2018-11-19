package servers;

import models.Project;


public interface CenterServerInterface 
{	
	String createMRecord(String managerId,
			String firstName, String lastName, String employeeID,
			String mailID, Project project, String location);
	String createERecord(String managerID,
			String firstName, String lastName, int employeeID, String mailID,
			String projectId);
	String getRecordCounts(String managerID);
	String editRecord(String managerID, String recordID,
			String fieldName, String newValue);
	String transferRecord(String managerID,
			String recordID, String remoteCenterServerName);
}
