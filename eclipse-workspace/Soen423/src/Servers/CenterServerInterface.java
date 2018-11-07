package Servers;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import Models.Project;

@WebService
@SOAPBinding(style=Style.RPC)
public interface CenterServerInterface 
{	
	String createMRecord(String managerId,
			String firstName, String lastName, String employeeID,
			String mailID, Project project, String location);
	String createERecord(String managerID,
			String firstName, String lastName, short employeeID, String mailID,
			String projectId);
	String getRecordCounts(String managerID);
	String editRecord(String managerID, String recordID,
			String fieldName, String newValue);
	String getMapCount();
	String transferRecord(String managerID,
			String recordID, String remoteCenterServerName);
}
